package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.cdi.Cached;
import org.dmly.traveller.app.infra.util.ReflectionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Cached
public class CachedFieldProvider extends FieldProvider {
    private final Map<String, List<String>> cache;

    private final Map<String, List<String>> domainFields;

    public CachedFieldProvider() {
        cache = new HashMap<>();
        domainFields = new ConcurrentHashMap<>();
    }

    @Override
    public List<String> getFieldNames(Class<?> source, Class<?> destination) {
        String key = source.getSimpleName() + destination.getSimpleName();
        List<String> fields = cache.get(key);

        if (fields == null) {
            fields = ReflectionUtil.findSimilarFields(source, destination);
            cache.put(key, fields);
        }

        return fields;
    }

    @Override
    public List<String> getDomainProperties(Class<?> clz) {
        String key = clz.getSimpleName();
        return domainFields.computeIfAbsent(key, item -> super.getDomainProperties(clz));
    }
}
