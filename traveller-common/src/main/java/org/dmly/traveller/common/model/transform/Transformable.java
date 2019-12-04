package org.dmly.traveller.common.model.transform;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface Transformable<T, P> {

    default P transform(T t, P p) {
        return p;
    }

    default T untransform(P p, T t) {
        return t;
    }

    default List<Field> getIgnoredFields() {
        return List.of();
    }

    default Map<String, String> getSourceMapping() {
        return Map.of();
    }
}
