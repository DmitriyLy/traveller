package org.dmly.traveller.presentation.admin.bean.transform;

import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.common.model.transform.TransformableProvider;

import java.util.Optional;

public class AdminTransformableProvider implements TransformableProvider {
    @Override
    public <T, P> Optional<Transformable<T, P>> find(Class<T> classT) {
        return Optional.empty();
    }
}
