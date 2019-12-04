package org.dmly.traveller.common.model.transform.impl;

import org.dmly.traveller.common.infra.exception.ConfigurationException;
import org.dmly.traveller.common.infra.util.ReflectionUtil;
import org.dmly.traveller.common.infra.cdi.Cached;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.entity.loader.EntityLoader;
import org.dmly.traveller.common.model.transform.TransformableProvider;
import org.dmly.traveller.common.model.transform.Transformer;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Field;
import java.util.Map;

@Named
public class EntityReferenceTransformer implements Transformer {

    private final EntityLoader entityLoader;

    private final FieldProvider fieldProvider;

    private final Transformer delegate;

    private final TransformableProvider transformableProvider;

    @Inject
    public EntityReferenceTransformer(@DBSource EntityLoader entityLoader, @Cached FieldProvider fieldProvider,
                                      TransformableProvider transformableProvider) {
        this.entityLoader = entityLoader;
        this.fieldProvider = fieldProvider;
        this.delegate = new SimpleDTOTransformer(fieldProvider, transformableProvider);
        this.transformableProvider = transformableProvider;
    }

    @Override
    public <T extends AbstractEntity, P> P transform(final T entity, final P destination) {
        Class<T> clz = (Class<T>) entity.getClass();

        Map<String, String> mapping = transformableProvider.find(clz).map(t -> t.getSourceMapping()).orElse(Map.of());

        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String name = entry.getKey();
            String domainProperty = entry.getValue();
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
    public <T extends AbstractEntity, P> T untransform(final P dto, final T entity) {
        Class<T> clz = (Class<T>) entity.getClass();
        Map<String, String> mapping = transformableProvider.find(clz).map(t -> t.getSourceMapping()).orElse(Map.of());

        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String name = entry.getKey();
            String domainProperty = entry.getValue();

            Field dstField = ReflectionUtil.getField(entity.getClass(), domainProperty);
            int id = (int) ReflectionUtil.getFieldValue(dto, name);
            if (id > 0) {
                AbstractEntity value = entityLoader.load((Class) dstField.getType(), id);
                ReflectionUtil.setFieldValue(entity, domainProperty, value);
            }
        }
        delegate.untransform(dto, entity);

        return entity;
    }
}
