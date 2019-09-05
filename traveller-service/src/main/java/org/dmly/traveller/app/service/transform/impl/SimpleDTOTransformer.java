package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.Checks;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;
import org.dmly.traveller.app.service.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDTOTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);

    @Override
    public <T extends AbstractEntity, P extends BaseDTO<T>> P transform(final T entity, final Class<P> clz) {
        checkParams(entity, clz);

        P dto = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(entity, dto, ReflectionUtil.findSimilarField(entity.getClass(), clz));
        dto.transform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(dto));
        }

        return dto;
    }

    @Override
    public <T extends AbstractEntity, P extends BaseDTO<T>> T untransform(final P dto, final Class<T> clz) {
        checkParams(dto, clz);

        T entity = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(dto, entity, ReflectionUtil.findSimilarField(dto.getClass(), clz));
        dto.untransform(entity);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.untransform: {} entity", CommonUtil.toString(entity));
        }

        return entity;
    }

    private void checkParams(final Object param, final Class<?> clz) {
        Checks.checkParameter(param != null, "Source transform object is not initialized");
        Checks.checkParameter(clz != null, "No class is defined for transformation");
    }
}
