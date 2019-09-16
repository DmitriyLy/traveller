package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.infra.exception.flow.ValidationException;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.StationRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateStationRepository;
import org.dmly.traveller.app.service.GeographicService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class GeographicServiceImplTest {
    private static final int DEFAULT_CITY_ID = 1;

    private static GeographicService service;

    private static ExecutorService executorService;

    @BeforeClass
    public static void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        CityRepository repository = new HibernateCityRepository(builder);
        StationRepository stationRepository = new HibernateStationRepository(builder);
        service = new GeographicServiceImpl(repository, stationRepository);

        executorService = Executors.newCachedThreadPool();
    }

    @AfterClass
    public static void tearDown() {
        executorService.shutdownNow();
    }


    @Test
    public void testNoDataReturnedAtStart() {
        List<City> cities = service.findCities();
        assertTrue(!cities.isEmpty());
    }

    @Test
    public void testSaveNewCitySuccess() {
        int cityCount = service.findCities().size();

        City city = createCity();
        service.saveCity(city);

        List<City> cities = service.findCities();
        assertEquals(cities.size(), cityCount + 1);
        assertEquals(cities.get(0).getName(), "Odessa");
    }

    @Test
    public void testFindCityByIdSuccess() {
        City city = createCity();
        service.saveCity(city);

        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertTrue(foundCity.isPresent());
        assertEquals(foundCity.get().getId(), DEFAULT_CITY_ID);
    }

    @Test
    public void testFindCityByIdNotFound() {
        Optional<City> foundCity = service.findCityById(DEFAULT_CITY_ID);
        assertFalse(!foundCity.isPresent());
    }

    @Test
    public void testSearchStationsByNameSuccess() {
        City city = createCity();
        city.addStation(TransportType.AUTO);
        city.addStation(TransportType.RAILWAY);
        service.saveCity(city);

        List<Station> stations = service.searchStations(StationCriteria.byName("Odessa"), new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertEquals(stations.size(), 2);
        assertEquals(stations.get(0).getCity(), city);
    }

    @Test
    public void testSearchStationsByNameNotFound() {
        List<Station> stations = service.searchStations(StationCriteria.byName("London"), new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }

    @Test
    public void testSearchStationsByTransportTypeSuccess() {
        int stationCount = service.searchStations(StationCriteria.byTransportType(TransportType.AUTO), new RangeCriteria(1, 5))
                .size();

        City city = createCity();
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kiev");
        city2.setDistrict("Kiev");
        city2.setRegion("Kiev");
        city2.addStation(TransportType.AUTO);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(StationCriteria.byTransportType(TransportType.AUTO),
                new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertEquals(stations.size(), stationCount + 2);
    }

    @Test
    public void testSearchStationsByTransportTypeNotFound() {
        City city = createCity();
        city.addStation(TransportType.AUTO);
        service.saveCity(city);
        City city2 = new City("Kiev");
        city2.setId(2);
        city2.addStation(TransportType.RAILWAY);
        service.saveCity(city2);

        List<Station> stations = service.searchStations(StationCriteria.byTransportType(TransportType.AVIA), new RangeCriteria(1, 5));
        assertNotNull(stations);
        assertTrue(stations.isEmpty());
    }

    @Test
    public void testSaveMultipleCitiesSuccess() {
        int cityCount = service.findCities().size();

        int addedCount = 100_000;

        for (int i = 0; i < addedCount; i++) {
            City city = new City("Odessa" + i);
            city.setDistrict("Odessa");
            city.setRegion("Odessa");
            city.addStation(TransportType.AUTO);
            service.saveCity(city);
        }

        List<City> cities = service.findCities();
        assertEquals(cityCount + addedCount, cities.size());
    }

    @Test
    public void testSaveMultipleCitiesConcurrentlySuccess() {
        int cityCount = service.findCities().size();

        int threadCount = 200;
        int batchCount = 10;

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            futures.add(executorService.submit(() -> {
                for (int j = 0; j < batchCount; j++) {
                    City city = new City("Lviv_" + Math.random());
                    city.setDistrict("Lviv");
                    city.setRegion("Lviv");
                    city.addStation(TransportType.AUTO);
                    service.saveCity(city);
                }
            }));
        }

        waitForFutures(futures);

        List<City> cities = service.findCities();
        assertEquals(cityCount + threadCount * batchCount, cities.size());
    }

    @Test
    public void testSaveOneCityConcurrentlySuccess() {
        City city = new City("Nikolaev");
        city.setDistrict("Nikolaev");
        city.setRegion("Nikolaev");
        city.addStation(TransportType.AUTO);

        service.saveCity(city);

        int cityCount = service.findCities().size();

        int threadCount = 200;

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            futures.add(executorService.submit(() -> {
                city.setName("Nikolaev_" + Math.random());
                service.saveCity(city);
            }));
        }

        waitForFutures(futures);
    }

    @Test
    public void testSaveCityMissingNameValidationExceptionThrown() {
        try {
            City city = new City();
            city.setDistrict("Nikolaev");
            city.setRegion("Nikolaev");
            service.saveCity(city);

            fail("City name validation failed");
        } catch (ValidationException e) {
            assertTrue(e.getMessage().contains("name:may not be null"));
        }
    }

    @Test
    public void testSaveCityNameTooShortValidationExceptionThrown() {
        try {
            City city = new City("N");
            city.setDistrict("Nikolaev");
            city.setRegion("Nikolaev");
            service.saveCity(city);

            fail("City name validation failed");
        } catch (ValidationException ex) {
            assertTrue(ex.getMessage().contains("name:size must be between 2 and 32"));
        }
    }

    @Test
    public void testSaveCityNameTooLongValidationExceptionThrown() {
        try {
            City city = new City("jkHJJJhj454346JHUujP{+OIHJOI5444JIJKJIJOJVYY");
            city.setDistrict("Nikolaev");
            city.setRegion("Nikolaev");
            service.saveCity(city);

            fail("City name validation failed");
        } catch (ValidationException ex) {
            assertTrue(ex.getMessage().contains("name:size must be between 2 and 32"));
        }
    }

    private void waitForFutures(List<Future<?>> futures) {
        futures.forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                fail(e.getMessage());
            }
        });
    }

    private City createCity() {
        City city = new City("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");

        return city;
    }
}