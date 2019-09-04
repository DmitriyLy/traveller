package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GeographicServiceImplTest {
    private GeographicService service;

    @Before
    public void setup() {
        service = new GeographicServiceImpl();
    }

    @Test
    public void testNoDataReturnedAtStart() {
        List<City> cities = service.findCities();

        assertTrue(cities.isEmpty());
    }

    @Test
    public void testSaveNewCitySuccess() {
        City city = new City("Odessa");
        service.saveCity(city);

        List<City> cities = service.findCities();
        assertEquals(1, cities.size());
        assertEquals("Odessa", cities.get(0).getName());
    }
}