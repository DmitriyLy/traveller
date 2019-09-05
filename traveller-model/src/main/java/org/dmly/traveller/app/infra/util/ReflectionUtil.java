package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.infra.util.annotation.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtil {

    private ReflectionUtil() {

    }

    /**
     * Creates an instance of the specified class. This method throws unchecked
     * exception if creation fails
     *
     * @param clz
     * @return
     * @throws ConfigurationException
     */
    public static <T> T createInstance(Class<T> clz) throws ConfigurationException {
        try {
            return clz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }

    public static List<String> findSimilarField(Class<?> clz1, Class<?> clz2) throws ConfigurationException {

        try {
            List<Field> fields = getFields(clz1);

            List<String> targetFields = getFields(clz2).stream()
                    .filter(field -> !field.isAnnotationPresent(Ignore.class))
                    .map(field -> field.getName())
                    .collect(Collectors.toList());

            return fields.stream()
                    .filter(field -> !field.isAnnotationPresent(Ignore.class))
                    .filter(field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()))
                    .map(field -> field.getName())
                    .filter(name -> targetFields.contains(name))
                    .collect(Collectors.toList());

        } catch (SecurityException e) {
            throw new ConfigurationException(e);
        }

    }

    public static <T> List<Field> getFields(Class<?> cls) {
        List<Field> fields = new ArrayList<>();
        while (cls != null) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            cls = cls.getSuperclass();
        }

        return fields;
    }

    public static void copyFields(Object source, Object destination, List<String> fields) throws ConfigurationException {
        Checks.checkParameter(source != null, "Source object is not initialized");
        Checks.checkParameter(destination != null, "Destination object is not initialized");

        try {
            for (String field : fields) {
                Field fieldSource = getField(source.getClass(), field);
                Field fieldDestination = getField(destination.getClass(), field);
                if (fieldSource != null && fieldDestination != null) {
                    fieldSource.setAccessible(true);
                    Object value = fieldSource.get(source);

                    fieldDestination.setAccessible(true);
                    fieldDestination.set(destination, value);
                }
            }
        } catch (SecurityException | ReflectiveOperationException e) {
            throw new ConfigurationException(e);
        }
    }

    public static <T> Field getField(final Class<T> clz, final String name) {
        Class<?> current = clz;
        while (current != null) {
            try {
                return current.getDeclaredField(name);
            } catch (NoSuchFieldException | SecurityException e) {
                current = current.getSuperclass();
            }
        }

        throw new ConfigurationException("No field " + name + " in the class " + clz);
    }
}
