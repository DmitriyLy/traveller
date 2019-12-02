package org.dmly.traveller.common.infra.exception.base;

/**
 * Base class for all application-specific exceptions
 *
 */
public class AppException extends RuntimeException {

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }
}
