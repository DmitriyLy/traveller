package org.dmly.traveller.app.rest.exception;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.junit.Assert.*;

public class GlobalExceptionHandlerTest {

    private ExceptionMapper<Exception> handler;

    @Before
    public void setUp() throws Exception {
        handler = new GlobalExceptionHandler();
    }

    @Test
    public void testResponseReturnsServerError() {
        Exception exception = new Exception("test");
        Response response = handler.toResponse(exception);
        assertEquals(response.getStatus(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
    }
}