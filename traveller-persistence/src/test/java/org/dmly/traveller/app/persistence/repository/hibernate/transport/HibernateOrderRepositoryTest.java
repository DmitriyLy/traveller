package org.dmly.traveller.app.persistence.repository.hibernate.transport;

import org.dmly.traveller.app.model.entity.geography.Address;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.model.entity.travel.Order;
import org.dmly.traveller.app.model.entity.travel.OrderState;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.model.entity.travel.Trip;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.app.persistence.repository.transport.OrderRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.*;

public class HibernateOrderRepositoryTest {
    private OrderRepository repository;

    private RouteRepository routeRepository;

    private CityRepository cityRepository;

    private Trip trip;

    @Before
    public void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        repository = new HibernateOrderRepository(builder);
        routeRepository = new HibernateRouteRepository(builder);
        cityRepository = new HibernateCityRepository(builder);

        City city = new City();
        city.setName("Odessa");
        city.setDistrict("Odessa");
        city.setRegion("Odessa");

        Station start = city.addStation(TransportType.AUTO);
        Address address1 = new Address();
        address1.setStreet("test1");
        start.setAddress(address1);

        Station destination = city.addStation(TransportType.AUTO);
        Address address2 = new Address();
        address2.setStreet("test2");
        destination.setAddress(address2);

        Route route = new Route();
        route.setStart(start);
        route.setDestination(destination);
        route.setStartTime(LocalTime.now());
        route.setEndTime(LocalTime.now());

        trip = new Trip();
        trip.setAvailableSeats(20);
        trip.setPrice(20);
        trip.setStartTime(LocalDateTime.now());
        trip.setEndTime(LocalDateTime.now().plusHours(2));

        route.addTrip(trip);

        cityRepository.save(city);
        routeRepository.save(route);
    }

    @Test
    public void save_ValidUserObject_UserSaved() {
        Order order = new Order();
        order.setClientName("User");
        order.setClientPhone("11111");
        order.setState(OrderState.CREATED);
        order.setDueDate(LocalDateTime.now().plusHours(1));
        order.setTrip(trip);

        repository.save(order);

        List<Order> orders = repository.findAll(1);
        assertEquals(orders.size(), 1);
    }

    @Test(expected=Exception.class)
    public void save_orderIsNull_ExceptionThrown() {
        repository.save(null);

        fail("Exception should be thrown");
    }

}