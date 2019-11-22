package org.dmly.traveller.app.rest.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private ExceptionMapper<Exception> handler;

    @BeforeEach
    void setUp() throws Exception {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void testResponseReturnsServerError() {
        Exception exception = new Exception("test");
        Response response = handler.toResponse(exception);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}