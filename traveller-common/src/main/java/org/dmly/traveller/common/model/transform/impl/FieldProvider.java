package org.dmly.traveller.common.model.transform.impl;

import org.dmly.traveller.common.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;

import java.lang.reflect.Field;
import java.util.List;

public class FieldProvider {

    public List<String> getFieldNames(Class<?> source, Class<?> dest) {
        return ReflectionUtil.findSimilarFields(source, dest);
    }

    public List<String> getFieldNames(Class<?> source, Class<?> dest, List<Field> ignoredFields) {
        return ReflectionUtil.findSimilarFields(source, dest, ignoredFields);
    }

    public List<String> getDomainProperties(Class<?> clz) {
        return ReflectionUtil.getFields(clz, List.of(field -> field.isAnnotationPresent(DomainProperty.class)));
    }

}
