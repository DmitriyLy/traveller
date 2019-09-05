package org.dmly.traveller.app.rest.service.base;

import javax.ws.rs.core.Response;

public abstract class BaseResource {
    protected final Response NOT_FOUND;
    protected final Response BAD_REQUEST;

    public BaseResource() {
        NOT_FOUND = Response.status(Response.Status.NOT_FOUND).build();
        BAD_REQUEST = Response.status(Response.Status.BAD_REQUEST).build();
    }

    protected Response ok(Object result) {
        return Response.ok(result).build();
    }
}
