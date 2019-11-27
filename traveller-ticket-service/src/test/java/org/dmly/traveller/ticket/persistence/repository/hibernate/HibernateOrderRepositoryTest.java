package org.dmly.traveller.ticket.persistence.repository.hibernate;

import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.ticket.model.entity.Order;
import org.dmly.traveller.ticket.model.entity.OrderState;
import org.dmly.traveller.ticket.persistence.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class HibernateOrderRepositoryTest {
    private OrderRepository repository;

    @Before
    public void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder(new StandardPropertyEnvironment());
        repository = new HibernateOrderRepository(builder);
    }

    @Test
    public void save_validOrderObject_orderSaved() {
        Order order = new Order();
        order.setClientName("User");
        order.setClientPhone("11111");
        order.setState(OrderState.CREATED);
        order.setDueDate(LocalDateTime.now().plusHours(1));
        order.setTripId("123");

        repository.save(order);

        List<Order> orders = repository.findAll(order.getTripId());
        assertEquals(orders.size(), 1);
    }

    @Test(expected = Exception.class)
    public void save_orderIsNull_ExceptionThrown() {
        repository.save(null);

        fail("Exception should be thrown");
    }
}