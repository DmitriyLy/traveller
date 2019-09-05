package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.service.GeographicService;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class GeographicServiceImplTest {
    private static final int DEFAULT_CITY_ID = 1;

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

    @Test
    public void testFindCityByIdNotFound() {
        Optional<City> city = service.findCityById(DEFAULT_CITY_ID);
        assertFalse(city.isPresent());
    }

    @Test
    public void testSearchStationsByNameSuccess() {
        City city = new City("Odessa");
        city.setId(DEFAULT_CITY_ID);
        city.addStation(TransportType.AUTO);
        city.addStation(TransportType.RAILWAY);
        service.saveCity(city);

        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertEquals(2, stations.size());
        assertEquals(city, stations.get(0).getCity());
    }

    @Test
    public void testSearchStationByNameNotFound() {
        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }

    @Test
    public void testSearchStationsByTransportTypeSuccess() {
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        service.saveCity(city);

        City city1 = new City("Kiev");
        city1.setId(2);
        city1.addStation(TransportType.AUTO);
        service.saveCity(city1);

        List<Station> stations = service.searchStations(StationCriteria.byTransportType(TransportType.AUTO),
                new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertEquals(2, stations.size());
    }

    @Test
    public void testSearchStationsByTransportTypeNotFound() {
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kiev");
        city2.setId(2);
        city2.addStation(TransportType.RAILWAY);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(StationCriteria.byTransportType(TransportType.AVIA),
                new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }
}