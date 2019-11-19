package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;

import java.util.List;

public class FieldProvider {
    public List<String> getFieldNames(Class<?> source, Class<?> destination) {
        return ReflectionUtil.findSimilarFields(source, destination);
    }

    public List<String> getDomainProperties(Class<?> clz) {
        return ReflectionUtil.getFields(clz, List.of(field -> field.isAnnotationPresent(DomainProperty.class)));
    }
}
