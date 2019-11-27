package org.dmly.traveller.geography.infra.cdi;

import org.dmly.traveller.common.infra.cdi.DBSource;
import org.glassfish.hk2.api.AnnotationLiteral;

public class DBSourceInstance extends AnnotationLiteral<DBSource> implements DBSource {
    public static DBSource get() {
        return new DBSourceInstance();
    }
}
