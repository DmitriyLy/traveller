package org.dmly.traveller.common.infra.exception;

import org.dmly.traveller.app.infra.exception.base.AppException;

public class PersistenceException extends AppException {
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(String message) {
        super(message);
    }
}
