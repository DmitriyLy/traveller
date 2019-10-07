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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    @SuppressWarnings("unchecked")
    @Test
    public void testSaveCitySuccess() throws Throwable {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Kiev");
        cityDTO.setDistrict("Kiev");
        cityDTO.setRegion("Kiev");

        CompletableFuture<Void> cf = target("cities").request().rx()
                .post(Entity.entity(cityDTO, MediaType.APPLICATION_JSON))
                .thenAccept(response ->
                    assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode())
                )
                .thenCompose(v -> target("cities").request().rx().get(Response.class))
                .thenAccept(response -> {
                    List<Map<String, String>> cities = response.readEntity(List.class);
                    assertNotNull(cities);
                    assertTrue(cities.stream().anyMatch(item -> item.get("name").equals("Kiev")));
                })
                .toCompletableFuture();

        try {
            cf.join();
        } catch (CompletionException e) {
            if (e.getCause() != null) {
                throw  e.getCause();
            }
            fail(e.getMessage());
        }
    }
}