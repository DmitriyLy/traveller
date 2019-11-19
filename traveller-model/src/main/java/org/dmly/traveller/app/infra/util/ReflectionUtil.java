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
            Predicate<Field> ignoreField = field -> !field.isAnnotationPresent(Ignore.class);
            List<String> targetFields = getFields(clz2, List.of(ignoreField));

            return getFields(clz1, List.of(ignoreField,
                    field -> !Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()),
                    field -> targetFields.contains(field.getName())));
        } catch (SecurityException ex) {
            throw new ConfigurationException(ex);
        }
    }

    public static <T> List<Field> getFields(final Class<?> cls) {
        List<Field> fields = new ArrayList<Field>();

        Class<?> current = cls;
        while (current != null) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }

        return fields;
    }

    public static List<String> getFields(final Class<?> cls, final List<Predicate<Field>> filters) {
        List<Field> fields = new ArrayList<Field>();

        Class<?> current = cls;
        while (current != null) {
            fields.addAll(Arrays.stream(current.getDeclaredFields())
                    .filter(field -> filters.stream().allMatch(p -> p.test(field))).collect(Collectors.toList()));
            current = current.getSuperclass();
        }

        return fields.stream().map(Field::getName).collect(Collectors.toList());
    }

    public static void copyFields(final Object src, final Object dest, final List<String> fields) throws ConfigurationException {
        Checks.checkParameter(src != null, "Source object is not initialized");
        Checks.checkParameter(dest != null, "Destination object is not initialized");
        try {
            for (String field : fields) {
                Field fld = getField(src.getClass(), field);
                // Skip unknown fields
                if (fld != null) {
                    fld.setAccessible(true);
                    Object value = fld.get(src);

                    setFieldValue(dest, field, value);
                }
            }
        } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new ConfigurationException(ex);
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
            Field fld = getField(src.getClass(), name);
            fld.setAccessible(true);
            return fld.get(src);
        } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new ConfigurationException(ex);
        }
    }

    public static void setFieldValue(final Object src, final String name, final Object value) throws ConfigurationException {
        Checks.checkParameter(src != null, "Source object is not initialized");
        Checks.checkParameter(!StringUtils.isEmpty(name), "Field name is not initialized");
        try {
            Field fld = getField(src.getClass(), name);
            fld.setAccessible(true);
            fld.set(src, value);
        } catch (SecurityException | ReflectiveOperationException | IllegalArgumentException ex) {
            throw new ConfigurationException(ex);
        }
    }
}
