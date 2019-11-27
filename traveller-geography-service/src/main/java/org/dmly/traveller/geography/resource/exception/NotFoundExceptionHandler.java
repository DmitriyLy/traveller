package org.dmly.traveller.geography.resource.exception;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<InvalidParameterException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionHandler.class);

    @Override
    public Response toResponse(InvalidParameterException e) {
        LOGGER.debug(e.getMessage(), e);

        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }
}
