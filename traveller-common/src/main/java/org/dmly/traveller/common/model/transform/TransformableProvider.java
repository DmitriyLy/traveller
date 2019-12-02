package org.dmly.traveller.common.model.transform;

import java.util.Optional;

@FunctionalInterface
public interface TransformableProvider {

    <T, P> Optional<Transformable<T, P>> find(Class<T> classT);

}
