package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.ReflectionUtil;

import java.util.List;

public class FieldProvider {
    public List<String> getFieldNames(Class<?> source, Class<?> destination) {
        return ReflectionUtil.findSimilarFields(source, destination);
    }
}
