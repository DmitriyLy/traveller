package org.dmly.traveller.common.model.entity.loader;

import org.dmly.traveller.common.model.entity.base.AbstractEntity;

@FunctionalInterface
public interface EntityLoader {

    <T extends AbstractEntity> T load(Class<T> clz, int id);

}
