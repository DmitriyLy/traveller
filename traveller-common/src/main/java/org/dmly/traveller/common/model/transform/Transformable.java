package org.dmly.traveller.common.model.transform;

public interface Transformable<T, P> {

    default P transform(T t, P p) {
        return p;
    }

    default T untransform(P p, T t) {
        return t;
    }
}
