package org.dmly.traveller.app.service.transform.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.dmly.traveller.app.infra.util.ReflectionUtil;

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
            fields = ReflectionUtil.findSimilarField(source, destination);
            cache.putAll(key, fields);
        }

        return fields;
    }
}
