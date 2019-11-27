package org.dmly.traveller.trip.service;

import org.dmly.traveller.trip.model.entity.Route;
import org.dmly.traveller.trip.model.entity.Trip;

import java.util.List;
import java.util.Optional;

public interface TripService {
    List<Route> findRoutes();

    Optional<Route> findRouteById(int id);

    void saveRoute(Route route);

    void deleteRoute(int routeId);

    List<Trip> findTrips(int routeId);

    Optional<Trip> findTripById(int id);

    void saveTrip(Trip trip);

    void deleteTrip(int tripId);
}
