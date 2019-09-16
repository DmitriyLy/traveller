package org.dmly.traveller.app.infra.exception.flow;

import org.dmly.traveller.app.infra.exception.FlowException;

import javax.validation.ConstraintViolation;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationException extends FlowException {

    public <T> ValidationException(String message, Set<ConstraintViolation<T>> constraints) {
        super(message + " : " +
                constraints.stream()
                        .map(constraint -> constraint.getPropertyPath() + ":" + constraint.getMessage())
                        .collect(Collectors.joining(",")));
    }
}
