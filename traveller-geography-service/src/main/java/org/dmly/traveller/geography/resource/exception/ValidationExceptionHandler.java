package org.dmly.traveller.geography.resource.exception;

import org.dmly.traveller.app.infra.exception.flow.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ValidationExceptionHandler implements ExceptionMapper<ValidationException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionHandler.class);

    @Override
    public Response toResponse(ValidationException e) {
        LOGGER.error(e.getMessage(), e);

        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }
}
