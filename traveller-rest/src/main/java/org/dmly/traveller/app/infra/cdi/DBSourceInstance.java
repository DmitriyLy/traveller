package org.dmly.traveller.app.infra.cdi;

import org.glassfish.hk2.api.AnnotationLiteral;

public class DBSourceInstance extends AnnotationLiteral<DBSource> implements DBSource {
    public static DBSource get() {
        return new DBSourceInstance();
    }
}
