package org.dmly.traveller.trip.persistence.repository;


import org.dmly.traveller.trip.model.entity.Route;

import java.util.List;
import java.util.Optional;

public interface RouteRepository {

    List<Route> findAll();

    Optional<Route> findById(int id);

    void save(Route route);

    void delete(int routeId);
}
