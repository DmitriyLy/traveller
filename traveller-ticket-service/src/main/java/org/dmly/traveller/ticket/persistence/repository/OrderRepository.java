package org.dmly.traveller.ticket.persistence.repository;

import org.dmly.traveller.ticket.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);

    List<Order> findAll(int tripId);

    Optional<Order> findById(int id);
}
