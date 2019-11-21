package org.dmly.traveller.app.service.transform.impl;

import lombok.RequiredArgsConstructor;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.transform.Transformable;
import org.dmly.traveller.app.service.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class SimpleDTOTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);

    private final FieldProvider provider;

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> T untransform(P dto, T entity) {
        ReflectionUtil.copyFields(dto, entity, provider.getFieldNames(dto.getClass(), entity.getClass()));
        dto.untransform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} entity", CommonUtil.toString(dto));
        }

        return entity;
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, P destination) {
        ReflectionUtil.copyFields(entity, destination, provider.getFieldNames(entity.getClass(), destination.getClass()));
        destination.transform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(destination));
        }

        return destination;
    }
}
