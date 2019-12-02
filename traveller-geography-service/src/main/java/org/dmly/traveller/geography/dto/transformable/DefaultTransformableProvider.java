package org.dmly.traveller.geography.dto.transformable;

import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.common.model.transform.TransformableProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultTransformableProvider implements TransformableProvider {

    private final Map<Class<?>, Transformable<?, ?>> transformables = new HashMap<>();

    public DefaultTransformableProvider() {
        transformables.put(Station.class, new StationTransformable());
    }

    @Override
    public <T, P> Optional<Transformable<T, P>> find(Class<T> classT) {
        return (Optional) Optional.ofNullable(transformables.get(classT));
    }
}
