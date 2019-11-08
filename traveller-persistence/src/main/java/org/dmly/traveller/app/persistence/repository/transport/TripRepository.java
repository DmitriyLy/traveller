package org.dmly.traveller.app.persistence.repository.transport;

import org.dmly.traveller.app.model.entity.travel.Trip;

import java.util.List;
import java.util.Optional;

public interface TripRepository {

    List<Trip> findAll(int routeId);

    Optional<Trip> findById(int id);

    void save(Trip trip);

    void delete(int tripId);

}
