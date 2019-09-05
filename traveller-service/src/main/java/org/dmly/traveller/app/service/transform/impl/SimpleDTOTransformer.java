package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.util.Checks;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;
import org.dmly.traveller.app.service.transform.Transformer;

public class SimpleDTOTransformer implements Transformer {
    @Override
    public <T extends AbstractEntity, P extends BaseDTO<T>> P transform(final T entity, final Class<P> clz) {
        checkParams(entity, clz);

        P dto = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(entity, dto, ReflectionUtil.findSimilarField(entity.getClass(), clz));
        dto.transform(entity);


        return dto;
    }

    @Override
    public <T extends AbstractEntity, P extends BaseDTO<T>> T untransform(final P dto, final Class<T> clz) {
        checkParams(dto, clz);

        T entity = ReflectionUtil.createInstance(clz);
        ReflectionUtil.copyFields(dto, entity, ReflectionUtil.findSimilarField(dto.getClass(), clz));
        dto.untransform(entity);

        return entity;
    }

    private void checkParams(final Object param, final Class<?> clz) {
        Checks.checkParameter(param != null, "Source transform object is not initialized");
        Checks.checkParameter(clz != null, "No class is defined for transformation");
    }
}
