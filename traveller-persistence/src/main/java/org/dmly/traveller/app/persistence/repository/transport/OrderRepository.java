package org.dmly.traveller.app.persistence.repository.transport;

import org.dmly.traveller.app.model.entity.travel.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);

    List<Order> findAll(int tripId);
}
