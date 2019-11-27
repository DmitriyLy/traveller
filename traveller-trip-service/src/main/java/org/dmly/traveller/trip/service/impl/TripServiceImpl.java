package org.dmly.traveller.trip.service.impl;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.trip.model.entity.Route;
import org.dmly.traveller.trip.model.entity.Trip;
import org.dmly.traveller.trip.persistence.repository.RouteRepository;
import org.dmly.traveller.trip.persistence.repository.TripRepository;
import org.dmly.traveller.trip.service.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TripServiceImpl implements TripService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripServiceImpl.class);

    private final RouteRepository routeRepository;

    private final TripRepository tripRepository;

    @Inject
    public TripServiceImpl(@DBSource RouteRepository routeRepository, @DBSource TripRepository tripRepository) {
        this.routeRepository = routeRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Route> findRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Optional<Route> findRouteById(int id) {
        return routeRepository.findById(id);
    }

    @Override
    public void saveRoute(Route route) {
        routeRepository.save(route);
    }

    @Override
    public void deleteRoute(int routeId) {
        routeRepository.delete(routeId);
    }

    @Override
    public List<Trip> findTrips(int routeId) {
        return tripRepository.findAll(routeId);
    }

    @Override
    public Optional<Trip> findTripById(int id) {
        return tripRepository.findById(id);
    }

    @Override
    public void saveTrip(Trip trip) {
        tripRepository.save(trip);
    }

    @Override
    public void deleteTrip(int tripId) {
        tripRepository.delete(tripId);
    }
}
