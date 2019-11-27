package org.dmly.traveller.geography.resource.exception;

import org.glassfish.jersey.server.ParamException.PathParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PathParamExceptionHandler implements ExceptionMapper<PathParamException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PathParamExceptionHandler.class);

    @Override
    public Response toResponse(PathParamException exception) {
        LOGGER.error(exception.getMessage(), exception);

        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
