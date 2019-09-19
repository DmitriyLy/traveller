package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.Checks;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.transform.Transformable;
import org.dmly.traveller.app.service.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDTOTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);

    private final FieldProvider fieldProvider;

    public SimpleDTOTransformer() {
        fieldProvider = new CachedFieldProvider();
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> P transform(final T entity, final Class<P> clz) {
        checkParams(entity, clz);

        P dto = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(entity, dto, fieldProvider.getFieldNames(entity.getClass(), clz));
        dto.transform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(dto));
        }

        return dto;
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> void transform(final T entity, final P destination) {
        checkParam(entity, "Source transformation object is not initialized");
        checkParam(destination, "Destination object is not initialized");

        handleTransformation(entity, destination);
    }

    @Override
    public <T extends AbstractEntity, P extends Transformable<T>> T untransform(final P dto, final Class<T> clz) {
        checkParams(dto, clz);

        T entity = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(dto, entity, fieldProvider.getFieldNames(dto.getClass(), clz));
        dto.untransform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.untransform: {} entity", CommonUtil.toString(entity));
        }

        return entity;
    }

    private void checkParams(final Object param, final Class<?> clz) {
        checkParam(param, "Source transform object is not initialized");
        checkParam(clz, "No class is defined for transformation");
    }

    private void checkParam(final Object param, final String errorMessage) {
        Checks.checkParameter(param != null, errorMessage);
    }

    private <T extends AbstractEntity, P extends Transformable<T>> P handleTransformation(final T entity, final P destination) {
        ReflectionUtil.copyFields(entity, destination, fieldProvider.getFieldNames(entity.getClass(), destination.getClass()));
        destination.transform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(destination));
        }

        return destination;
    }
}
