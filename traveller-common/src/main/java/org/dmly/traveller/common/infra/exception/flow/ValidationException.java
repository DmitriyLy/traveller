package org.dmly.traveller.common.infra.exception.flow;

import org.dmly.traveller.common.infra.exception.FlowException;

public class ValidationException extends FlowException {
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
