package org.dmly.traveller.app.infra.exception.flow;

import org.dmly.traveller.common.infra.exception.FlowException;

public class InvalidParameterException extends FlowException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
