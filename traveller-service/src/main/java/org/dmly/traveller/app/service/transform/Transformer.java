package org.dmly.traveller.app.service.transform;

import org.dmly.traveller.app.infra.util.Checks;
import org.dmly.traveller.app.infra.util.ReflectionUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.transform.Transformable;

public interface Transformer {
    default <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, Class<P> clz) {
        checkParams(entity, clz);

        P destination = ReflectionUtil.createInstance(clz);

        return transform(entity, destination);
    }

    private void checkParams(final Object param, final Class<?> clz) {
        checkParam(param, "Source transformation object is not initialized");
        checkParam(clz, "No class is defined for transformation");
    }

    private void checkParam(final Object param, final String errorMessage) {
        Checks.checkParameter(param != null, errorMessage);
    }

    <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, P destination);

    <T extends AbstractEntity, P extends Transformable<T>> T untransform(P dto, T entity);

    default <T extends AbstractEntity, P extends Transformable<T>> T untransform(P dto, Class<T> clz) {
        checkParams(dto, clz);

        T entity = ReflectionUtil.createInstance(clz);

        return untransform(dto, entity);
    }
}
