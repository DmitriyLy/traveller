package org.dmly.traveller.geography.infra.cdi;

import org.dmly.traveller.common.infra.cdi.Cached;
import org.glassfish.jersey.internal.inject.AnnotationLiteral;

public class CachedInstance extends AnnotationLiteral<Cached> implements Cached {
    public static Cached get() {
        return new CachedInstance();
    }
}
