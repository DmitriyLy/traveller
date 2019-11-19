package org.dmly.traveller.app.service.transform.impl;

import lombok.RequiredArgsConstructor;
import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.model.transform.Transformable;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;
import org.dmly.traveller.app.service.transform.Transformer;


import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public class EntityReferenceTransformer implements Transformer {

    private final EntityLoader entityLoader;

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, Class<P> clz) {
        P dto = ReflectionUtil.createInstance(clz);
        transform(entity, dto);

        return dto;
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> void transform(T entity, P destination) {
        List<String> markedFields = ReflectionUtil.getFields(destination.getClass(),
                List.of(field -> field.isAnnotationPresent(DomainProperty.class)));

        for (String name : markedFields) {
            String domainProperty = ReflectionUtil.getField(destination.getClass(), name)
                    .getAnnotation(DomainProperty.class).value();
            Object value = ReflectionUtil.getFieldValue(entity, domainProperty);

            if (!(value instanceof AbstractEntity)) {
                throw new ConfigurationException(
                        "Reference property value of the domain object " + entity + " is not an entity: " + value);
            }

            AbstractEntity ref = (AbstractEntity) value;
            int id = ref.getId();
            ReflectionUtil.setFieldValue(destination, name, id);
        }
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> T untransform(P dto, Class<T> clz) {
        T entity = ReflectionUtil.createInstance(clz);

        List<String> markedFields = ReflectionUtil.getFields(dto.getClass(),
                List.of(field -> field.isAnnotationPresent(DomainProperty.class))
        );

        for (String name : markedFields) {
            String domainProperty = ReflectionUtil.getField(dto.getClass(), name)
                    .getAnnotation(DomainProperty.class).value();

            Field dstField = ReflectionUtil.getField(clz, domainProperty);
            int id = (int) ReflectionUtil.getFieldValue(dto, name);

            AbstractEntity value = entityLoader.load((Class)dstField.getType(), id);
            ReflectionUtil.setFieldValue(entity, domainProperty, value);

        }

        return entity;
    }
}
