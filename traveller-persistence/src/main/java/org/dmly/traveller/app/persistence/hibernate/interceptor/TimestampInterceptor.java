package org.dmly.traveller.app.persistence.hibernate.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TimestampInterceptor extends EmptyInterceptor {

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        int index = ArrayUtils.indexOf(propertyNames, AbstractEntity.FIELD_CREATED_AT);
        if (index >= 0) {
            state[index] = LocalDateTime.now();
            return true;
        }
        return false;
    }
}
