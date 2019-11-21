package org.dmly.traveller.app.infra.cdi;

import org.glassfish.jersey.internal.inject.AnnotationLiteral;

public class CachedInstance extends AnnotationLiteral<Cached> implements Cached {
    public static Cached get() {
        return new CachedInstance();
    }
}
