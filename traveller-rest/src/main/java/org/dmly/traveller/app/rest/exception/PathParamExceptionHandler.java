package org.dmly.traveller.app.rest.exception;

import org.glassfish.jersey.server.ParamException.PathParamException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PathParamExceptionHandler implements ExceptionMapper<PathParamException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PathParamExceptionHandler.class);

    @Override
    public Response toResponse(PathParamException exception) {
        return null;
    }
}
