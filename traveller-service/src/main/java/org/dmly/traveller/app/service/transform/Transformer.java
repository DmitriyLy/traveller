package org.dmly.traveller.app.service.transform;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

public interface Transformer {
    <T extends AbstractEntity, P extends BaseDTO<T>> P transform(T entity, Class<P> clz);

    <T extends AbstractEntity, P extends BaseDTO<T>> T untransform(P dto, Class<T> clz);
}
