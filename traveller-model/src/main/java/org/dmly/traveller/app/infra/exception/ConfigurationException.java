package org.dmly.traveller.app.infra.exception;

import org.dmly.traveller.app.infra.exception.base.AppException;

public class ConfigurationException extends AppException {
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationException(String message) {
        super(message);
    }
}
