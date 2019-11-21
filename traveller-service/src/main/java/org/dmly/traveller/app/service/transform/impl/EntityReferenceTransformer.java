package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.cdi.Cached;
import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.model.transform.Transformable;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;
import org.dmly.traveller.app.service.transform.Transformer;
import org.hibernate.internal.util.config.ConfigurationException;


import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Field;
import java.util.List;

@Named
public class EntityReferenceTransformer implements Transformer {

    private final EntityLoader entityLoader;

    private final FieldProvider fieldProvider;

    private final Transformer delegate;

    @Inject
    public EntityReferenceTransformer(@DBSource EntityLoader entityLoader, @Cached FieldProvider fieldProvider) {
        this.entityLoader = entityLoader;
        this.fieldProvider = fieldProvider;
        this.delegate = new SimpleDTOTransformer(fieldProvider);
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, Class<P> clz) {
        P dto = ReflectionUtil.createInstance(clz);
        transform(entity, dto);

        return dto;
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> P transform(final T entity, final P destination) {
        List<String> markedFields = fieldProvider.getDomainProperties(destination.getClass());
        for (String name : markedFields) {
            String domainProperty = ReflectionUtil.getField(destination.getClass(), name).getAnnotation(DomainProperty.class)
                    .value();
            Object value = ReflectionUtil.getFieldValue(entity, domainProperty);
            if (!(value instanceof AbstractEntity)) {
                throw new ConfigurationException(
                        "Reference property value of the domain object " + entity + " is not an entity: " + value);
            }
            AbstractEntity ref = (AbstractEntity) value;
            int id = ref.getId();
            ReflectionUtil.setFieldValue(destination, name, id);
        }

        return delegate.transform(entity, destination);
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> T untransform(final P dto, final T entity) {

        List<String> markedFields = fieldProvider.getDomainProperties(dto.getClass());
        for (String name : markedFields) {
            String domainProperty = ReflectionUtil.getField(dto.getClass(), name).getAnnotation(DomainProperty.class)
                    .value();

            Field dstField = ReflectionUtil.getField(entity.getClass(), domainProperty);
            int id = (int) ReflectionUtil.getFieldValue(dto, name);

            AbstractEntity value = entityLoader.load((Class)dstField.getType(), id);
            ReflectionUtil.setFieldValue(entity, domainProperty, value);
        }
        delegate.untransform(dto, entity);

        return entity;
    }
}
