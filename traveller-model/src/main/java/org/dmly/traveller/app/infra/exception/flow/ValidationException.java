package org.dmly.traveller.app.infra.exception.flow;

import org.dmly.traveller.app.infra.exception.FlowException;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends FlowException {
    private Set<ConstraintViolation<?>> constraints;

    public ValidationException(String message, Set<ConstraintViolation<?>> constraints) {
        super(message + constraints);
        this.constraints = constraints;
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public Set<ConstraintViolation<?>> getConstraints() {
        return constraints;
    }
}
