package org.dmly.traveller.app.persistence.repository.transport;

import org.dmly.traveller.app.model.entity.travel.Route;

import java.util.List;
import java.util.Optional;

public interface RouteRepository {

    List<Route> findAll();

    Optional<Route> findById(int id);

    void save(Route route);

    void delete(int routeId);
}
