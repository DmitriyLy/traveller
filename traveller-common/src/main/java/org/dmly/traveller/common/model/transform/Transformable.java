package org.dmly.traveller.common.model.transform;

public interface Transformable<P> {

    void transform(P p);

    P untransform(P p);
}
