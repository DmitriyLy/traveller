package org.dmly.traveller.app.rest.service.transport;

import org.dmly.traveller.app.rest.dto.transport.RouteDTO;
import org.dmly.traveller.app.rest.resolver.ObjectMapperContextResolver;
import org.dmly.traveller.app.rest.service.config.JerseyConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RouteResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new JerseyConfig();
    }

    @Before
    public void setup() {
        getClient().register(ObjectMapperContextResolver.class);
    }

    @Test
    public void findAll_emptyRepository_emptyListReturned() {
        List<Map<String, String>> routes = target("routes").request().get(List.class);
        assertNotNull(routes);
        assertTrue(routes.isEmpty());
    }

    @Test
    public void findById_nonExistingIdentifier_notFoundReturned() {
        Response response = target("routes/20000").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void findById_invalidIdentifier_badRequestReturned() {
        Response response = target("routes/aaab").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void save_nullStartTime_badRequestReturned() {
        RouteDTO route = new RouteDTO();
        route.setDestinationId(1);
        route.setStartId(2);
        route.setEndTime(LocalTime.of(20, 10));
        route.setPrice(20);
        Response response = target("routes").request().post(Entity.entity(route, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    //@Ignore
    public void save_routeValid_successStatusReturned() {
        RouteDTO route = new RouteDTO();
        route.setDestinationId(1);
        route.setStartId(2);
        route.setStartTime(LocalTime.of(15, 45));
        route.setEndTime(LocalTime.of(20, 10));
        route.setPrice(20);
        Response response = target("routes").request().post(Entity.entity(route, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}