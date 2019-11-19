package org.dmly.traveller.app.infra.util;

import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.infra.util.annotation.Ignore;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
            return clz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }

    public static List<String> findSimilarFields(Class<?> clz1, Class<?> clz2) throws ConfigurationException {
        try {
            Predicate<Field> ignoredField = field -> !field.isAnnotationPresent(Ignore.class);
            List<String> targetFields = getFields(clz2, List.of(ignoredField));

            return getFields(clz1, List.of(ignoredField,
                    field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()),
                    field -> targetFields.contains(field.getName())
            ));
        } catch (SecurityException e) {
            throw new ConfigurationException(e);
        }
    }

    public static <T> List<Field> getFields(final Class<?> cls) {
        List<Field> fields = new ArrayList<>();

        Class<?> current = cls;
        while (current != null) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        return fields;
    }

    public static List<String> getFields(final Class<?> cls, final List<Predicate<Field>> filters) {
        List<Field> fields = new ArrayList<>();

        Class<?> current = cls;
        while (current != null) {
            fields.addAll(
                    Arrays.stream(current.getDeclaredFields())
                            .filter(field -> filters.stream().allMatch(p -> p.test(field)))
                            .collect(Collectors.toList())
            );
            current = current.getSuperclass();
        }

        return fields.stream().map(Field::getName).collect(Collectors.toList());
    }

    public static void copyFields(final Object source, final Object destination, final List<String> fields) throws ConfigurationException {
        Checks.checkParameter(source != null, "Source object is not initialized");
        Checks.checkParameter(destination != null, "Destination object is not initialized");

        try {
            for (String field : fields) {
                Field fieldSource = getField(source.getClass(), field);
                Field fieldDestination = getField(destination.getClass(), field);
                if (fieldSource != null && fieldDestination != null) {
                    fieldSource.setAccessible(true);
                    Object value = fieldSource.get(source);

                    setFieldValue(destination, field, value);
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

    public static Object getFieldValue(final Object src, final String name) throws ConfigurationException {
        Checks.checkParameter(src != null, "Source object is not initialized");
        Checks.checkParameter(!StringUtils.isEmpty(name), "Field name is not initialized");

        try {
            Field field = getField(src.getClass(), name);
            field.setAccessible(true);
            return field.get(src);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }

    public static void setFieldValue(final Object src, final String name, final Object value) throws ConfigurationException {
        Checks.checkParameter(src != null, "Source object is not initialized");
        Checks.checkParameter(!StringUtils.isEmpty(name), "Field name is not initialized");

        try {
            Field field = getField(src.getClass(), name);
            field.setAccessible(true);
            field.set(src, value);
        } catch (IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }
}
