package org.dmly.traveller.app.persistence.repository.hibernate.transport;

import org.dmly.traveller.app.model.entity.geography.Address;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class HibernateRouteRepositoryTest {
    private RouteRepository repository;

    private CityRepository cityRepository;

    private Station start;

    private Station destination;

    @Before
    public void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        repository = new HibernateRouteRepository(builder);
        cityRepository = new HibernateCityRepository(builder);

        City city = new City();
        city.setName("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");

        start = city.addStation(TransportType.AUTO);
        Address address1 = new Address();
        address1.setStreet("test1");
        start.setAddress(address1);

        destination = city.addStation(TransportType.AUTO);
        Address address2 = new Address();
        address2.setStreet("test2");
        destination.setAddress(address2);

        cityRepository.save(city);

        Route route = new Route();
        route.setStart(start);
        route.setDestination(destination);
        route.setStartTime(LocalTime.now());
        route.setEndTime(LocalTime.now().plusHours(2));
        repository.save(route);
    }

    @Test
    public void findAll_InitialInvokation_nonEmptyListReturned() {
        List<Route> routes = repository.findAll();
        assertFalse(routes.isEmpty());
    }

    @Test
    public void findById_nonExistingIdentifier_emptyOptionalReturned() {
        Optional<Route> result = repository.findById(1000);
        assertFalse(result.isPresent());
    }

    @Test
    public void save_validRouteObject_routeSaved() {
        int routeCount = repository.findAll().size();

        Route route = new Route();
        route.setStart(start);
        route.setDestination(destination);
        route.setStartTime(LocalTime.now());
        route.setEndTime(LocalTime.now());
        repository.save(route);

        List<Route> routes = repository.findAll();
        assertEquals(routes.size(), routeCount + 1);
    }

    @Test(expected = Exception.class)
    public void save_routeIsNull_ExceptionThrown() {
        repository.save(null);

        fail("Exception should be thrown");
    }
}