package org.dmly.traveller.app.model.transform;

public interface Transformable<P> {

    void transform(P p);

    P untransform(P p);
}
