package org.dmly.traveller.app.infra.util;

import org.dmly.traveller.app.infra.exception.ConfigurationException;

import java.lang.reflect.Field;
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
            Field[] fields = clz1.getDeclaredFields();
            List<String> targetFields = Stream.of(clz2.getDeclaredFields())
                    .map(field -> field.getName())
                    .collect(Collectors.toList());

            return Stream.of(fields)
                    .map(field -> field.getName())
                    .filter(name -> targetFields.contains(name))
                    .collect(Collectors.toList());
        } catch (SecurityException e) {
            throw new ConfigurationException(e);
        }

    }

    public static void copyFields(Object source, Object destination, List<String> fields) throws ConfigurationException {
        Checks.checkParameter(source != null, "Source object is not initialized");
        Checks.checkParameter(destination != null, "Destination object is not initialized");

        try {
            for (String field : fields) {
                Field fieldSource = source.getClass().getDeclaredField(field);
                Field fieldDestination = destination.getClass().getDeclaredField(field);
                if (fieldSource != null && fieldDestination != null) {
                    fieldSource.setAccessible(true);
                    Object value = fieldSource.get(source);

                    fieldDestination.setAccessible(true);
                    fieldDestination.set(destination, value);
                }
            }
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            throw new ConfigurationException(e);
        }
    }
}
