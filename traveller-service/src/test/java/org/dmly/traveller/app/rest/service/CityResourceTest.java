package org.dmly.traveller.app.rest.service;

import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.rest.service.config.JerseyConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CityResourceTest  extends JerseyTest {

    @Override
    protected Application configure() {
        return new JerseyConfig();
    }

    @Test
    public void testFindCitiesSuccess() {
        List<Map<String, String>> cities = target("cities").request().get(List.class);
        assertNotNull(cities);
        assertFalse(cities.isEmpty());
    }

    @Test
    @Ignore
    public void testFindCityByIdSuccess() {
        CityDTO cityDTO = target("cities/1").request().get(CityDTO.class);

        assertNotNull(cityDTO);
        assertEquals(1, cityDTO.getId());
        assertEquals("Odessa", cityDTO.getName());
    }

    @Test
    public void testFindCityByIdNotFound() {
        Response response = target("cities/2").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testFindCityByIdInvalidId() {
        Response response = target("cities/aaab").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void testSaveCitySuccess() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Kiev");
        cityDTO.setDistrict("Kiev");
        cityDTO.setRegion("Kiev");

        Response response = target("cities").request().post(Entity.entity(cityDTO, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}