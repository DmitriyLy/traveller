package org.dmly.traveller.app.rest.service;

import org.dmly.traveller.app.jersey.extension.JerseyTestExtension;
import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.rest.service.config.JerseyConfig;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

public class CityResourceTest {

    @RegisterExtension
    JerseyTestExtension extension = new JerseyTestExtension(configure());

    private Application configure() {
        return new JerseyConfig();
    }

    @org.junit.jupiter.api.Test
    void testFindCitiesSuccess(WebTarget target) {
        List<Map<String, String>> cities = target.path("cities").request().get(List.class);
        assertNotNull(cities);
        assertTrue(cities.isEmpty());
    }

    @org.junit.jupiter.api.Test
    @Disabled
    void testFindCityByIdSuccess(WebTarget target) {
        CityDTO city = target.path("cities/1").request().get(CityDTO.class);
        assertNotNull(city);
        assertEquals(city.getId(), 1);
        assertEquals(city.getName(), "Odessa");
    }

    @org.junit.jupiter.api.Test
    void testFindCityByIdNotFound(WebTarget target) {
        Response response = target.path("cities/20000").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @org.junit.jupiter.api.Test
    void testFindCityByIdInvalidId(WebTarget target) {
        Response response = target.path("cities/aaab").request().get(Response.class);
        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @org.junit.jupiter.api.Test
    void save_emptyName_badRequestReturned(WebTarget target) {
        CityDTO city = new CityDTO();
        city.setDistrict("Nikolaev");
        city.setRegion("Nikolaev");
        Response response = target.path("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @org.junit.jupiter.api.Test
    void save_cityValid_successStatusReturned(WebTarget target) {
        CityDTO city = new CityDTO();
        city.setName("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");
        Response response = target.path("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getHeaderString(HttpHeaders.LOCATION));
    }

    @org.junit.jupiter.api.Test
    void save_nameTooShort_badRequestReturned(WebTarget target) {
        CityDTO city = new CityDTO();
        city.setName("N");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");
        Response response = target.path("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @org.junit.jupiter.api.Test
    public void save_nameTooLong_badRequestReturned(WebTarget target) {
        CityDTO city = new CityDTO();
        city.setName("N1234567890123456789012345678901234567890");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");
        Response response = target.path("cities").request().post(Entity.entity(city, MediaType.APPLICATION_JSON_TYPE));
        assertNotNull(response);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testSaveCitySuccess(WebTarget target) throws Throwable {
        CityDTO city = new CityDTO();
        city.setName("Kiev");
        city.setDistrict("Kiev");
        city.setRegion("Kiev");

        CompletableFuture<Void> cf = target.path("cities").request().rx()
                .post(Entity.entity(city, MediaType.APPLICATION_JSON))
                .thenAccept(response -> assertEquals(response.getStatus(), Response.Status.CREATED.getStatusCode()))
                .thenCompose(v -> target.path("cities").request().rx().get(Response.class)).thenAccept(response -> {
                    List<Map<String, String>> cities = (List<Map<String, String>>) response.readEntity(List.class);
                    assertNotNull(cities);
                    assertTrue(cities.stream().anyMatch(item -> item.get("name").equals("Kiev")));
                }).toCompletableFuture();

        try {
            cf.join();
        } catch (CompletionException e) {
            if (e.getCause() != null) {
                throw e.getCause();
            }
            fail(e.getMessage());
        }
    }

}