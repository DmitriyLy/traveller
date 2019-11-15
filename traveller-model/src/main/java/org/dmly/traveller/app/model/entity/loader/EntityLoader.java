package org.dmly.traveller.app.model.entity.loader;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;

@FunctionalInterface
public interface EntityLoader {

    <T extends AbstractEntity> T load(Class<T> clz, int id);

}
