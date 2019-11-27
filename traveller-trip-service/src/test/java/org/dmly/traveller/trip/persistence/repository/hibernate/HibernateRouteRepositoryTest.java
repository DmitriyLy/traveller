package org.dmly.traveller.trip.persistence.repository.hibernate;

import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.trip.model.entity.Route;
import org.dmly.traveller.trip.persistence.repository.RouteRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class HibernateRouteRepositoryTest {
    private RouteRepository repository;

    private String start;

    private String destination;

    @Before
    public void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder(new StandardPropertyEnvironment());
        repository = new HibernateRouteRepository(builder);
        start = "123";
        destination = "456";

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