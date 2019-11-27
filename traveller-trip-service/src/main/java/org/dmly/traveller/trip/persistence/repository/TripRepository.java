package org.dmly.traveller.trip.persistence.repository;

import org.dmly.traveller.trip.model.entity.Trip;

import java.util.List;
import java.util.Optional;

public interface TripRepository {

    List<Trip> findAll(int routeId);

    Optional<Trip> findById(int id);

    void save(Trip trip);

    void delete(int tripId);

}
