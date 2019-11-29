package org.dmly.traveller.common.infra.exception;

import org.dmly.traveller.app.infra.exception.base.AppException;

public class FlowException extends AppException {
    public FlowException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlowException(String message) {
        super(message);
    }
}
