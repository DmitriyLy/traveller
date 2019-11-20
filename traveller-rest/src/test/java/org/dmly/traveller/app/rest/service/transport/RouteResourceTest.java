package org.dmly.traveller.app.rest.service.transport;

import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.rest.dto.StationDTO;
import org.dmly.traveller.app.rest.dto.transport.RouteDTO;
import org.dmly.traveller.app.rest.resolver.ObjectMapperContextResolver;
import org.dmly.traveller.app.rest.service.config.JerseyConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
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
    private static final int CITY_ID = 1;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        ResourceConfig config = new JerseyConfig().property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        return config;
    }

    @Before
    public void setup() {
        getClient().register(ObjectMapperContextResolver.class);

        CityDTO city = new CityDTO();
        city.setName("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");
        target("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON_TYPE));
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
    public void save_routeValid_successStatusReturned() {
        StationDTO start = new StationDTO();
        start.setCityId(CITY_ID);
        start.setTransportType(TransportType.AUTO.toString());

        Response response = target("stations").request().post(Entity.entity(start, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        StationDTO dest = new StationDTO();
        dest.setCityId(CITY_ID);
        dest.setTransportType(TransportType.AUTO.toString());

        response = target("stations").request().post(Entity.entity(dest, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        RouteDTO route = new RouteDTO();
        route.setDestinationId(1);
        route.setStartId(2);
        route.setStartTime(LocalTime.of(15, 45));
        route.setEndTime(LocalTime.of(20, 10));
        route.setPrice(20);
        response = target("routes").request().post(Entity.entity(route, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}