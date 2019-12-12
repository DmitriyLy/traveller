package org.dmly.traveller.common.model.transform.impl;


import lombok.RequiredArgsConstructor;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.common.infra.util.ReflectionUtil;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.common.model.transform.TransformableProvider;
import org.dmly.traveller.common.model.transform.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

@RequiredArgsConstructor
public class SimpleDTOTransformer implements Transformer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDTOTransformer.class);

    private final FieldProvider provider;

    private final TransformableProvider transformableProvider;

    @Override
    public <T, P> T untransform(final P dto, final T entity) {
        Class<T> clz = (Class<T>) entity.getClass();

        ReflectionUtil.copyFields(dto, entity,
                provider.getFieldNames(dto.getClass(), entity.getClass(), getIgnoredFields(clz)));

        transformableProvider.find(clz).ifPresent(transformable -> transformable.untransform(dto, entity));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} entity", CommonUtil.toString(dto));
        }

        return entity;
    }

    private <T, P> List<Field> getIgnoredFields(Class<T> clz) {
        return transformableProvider.find(clz).map(Transformable::getIgnoredFields).orElse(List.of());
    }

    @Override
    public <T extends AbstractEntity, P> P transform(final T entity, final P dest) {
        Class<T> clz = (Class<T>) entity.getClass();

        ReflectionUtil.copyFields(entity, dest,
                provider.getFieldNames(entity.getClass(), dest.getClass(), getIgnoredFields(clz)));

        transformableProvider.find(clz).ifPresent(transformable -> transformable.transform(entity, dest));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("SimpleDTOTransformer.transform: {} DTO object", CommonUtil.toString(dest));
        }
        return dest;
    }

}
