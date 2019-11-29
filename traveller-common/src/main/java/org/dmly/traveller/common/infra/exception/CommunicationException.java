package org.dmly.traveller.common.infra.exception;

import org.dmly.traveller.app.infra.exception.base.AppException;

public class CommunicationException extends AppException {
    public CommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommunicationException(String message) {
        super(message);
    }
}
