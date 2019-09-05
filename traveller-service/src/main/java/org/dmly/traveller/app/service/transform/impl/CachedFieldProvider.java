package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.ReflectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CachedFieldProvider extends FieldProvider {
    private final Map<String, List<String>> cache;

    public CachedFieldProvider() {
        cache = new HashMap<>();
    }

    @Override
    public List<String> getFieldNames(Class<?> source, Class<?> destination) {
        String key = source.getSimpleName() + destination.getSimpleName();
        List<String> fields = cache.get(key);

        if (fields == null) {
            fields = ReflectionUtil.findSimilarField(source, destination);
            cache.put(key, fields);
        }

        return fields;
    }
}
