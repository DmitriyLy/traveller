package org.dmly.traveller.app.service.transform;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.transform.Transformable;

public interface Transformer {
    <T extends AbstractEntity, P extends Transformable<T>> P transform(T entity, Class<P> clz);

    <T extends AbstractEntity, P extends Transformable<T>> void transform(T entity, P destination);

    <T extends AbstractEntity, P extends Transformable<T>> T untransform(P dto, Class<T> clz);
}
