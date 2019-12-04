package org.dmly.traveller.common.model.transform.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.dmly.traveller.common.infra.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.List;

public class GuavaCachedFieldProvider extends FieldProvider {
    private final ListMultimap<String, String> cache;

    public GuavaCachedFieldProvider() {
        cache = ArrayListMultimap.create();
    }

    @Override
    public List<String> getFieldNames(Class<?> source, Class<?> destination) {
        String key = source.getSimpleName() + destination.getSimpleName();

        List<String> fields = cache.get(key);
        if (fields == null) {
            fields = ReflectionUtil.findSimilarFields(source, destination);
            cache.putAll(key, fields);
        }

        return fields;
    }

    @Override
    public List<String> getFieldNames(Class<?> source, Class<?> dest, List<Field> ignoredFields) {
        String key = source.getSimpleName() + dest.getSimpleName();

        List<String> fields = cache.get(key);
        if (fields == null) {
            fields = ReflectionUtil.findSimilarFields(source, dest, ignoredFields);
            cache.putAll(key, fields);
        }

        return fields;
    }
}
