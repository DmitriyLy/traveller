package org.dmly.traveller.common.model.transform.impl;


import lombok.RequiredArgsConstructor;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.common.infra.util.ReflectionUtil;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.transform.TransformableProvider;
import org.dmly.traveller.common.model.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class SimpleDTOTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);

    private final FieldProvider provider;

    private final TransformableProvider transformableProvider;

    @Override
    public <T extends AbstractEntity, P > T untransform(final P dto, final T entity) {
        ReflectionUtil.copyFields(dto, entity, provider.getFieldNames(dto.getClass(), entity.getClass()));

        Class<T> clz = (Class<T>) entity.getClass();
        transformableProvider.find(clz).ifPresent(transformable -> transformable.untransform(dto, entity));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} entity", CommonUtil.toString(dto));
        }

        return entity;
    }

    @Override
    public <T extends AbstractEntity, P> P transform(final T entity, final P destination) {
        ReflectionUtil.copyFields(entity, destination, provider.getFieldNames(entity.getClass(), destination.getClass()));

        Class<T> clz = (Class<T>) entity.getClass();
        transformableProvider.find(clz).ifPresent(transformable -> transformable.transform(entity, destination));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(destination));
        }
        return destination;
    }
}
