package org.dmly.traveller.app.rest.service.base;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public abstract class BaseResource {
    private static final String SERVICE_GET_NAME = "findById";

    @Context
    private UriInfo uriInfo;

    protected final Response NOT_FOUND;

    protected final Response BAD_REQUEST;

    public BaseResource() {
        NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
        BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();
    }

    protected Response ok(Object result) {
        return Response.ok(result).build();
    }

    protected Response postForLocation(int id) {
        URI uri = uriInfo.getRequestUriBuilder().path(getClass(), SERVICE_GET_NAME).build(id);
        return Response.created(uri).build();
    }
}
