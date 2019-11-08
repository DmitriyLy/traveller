package org.dmly.traveller.app.service;

import org.dmly.traveller.app.model.entity.travel.Order;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.model.entity.travel.Ticket;
import org.dmly.traveller.app.model.entity.travel.Trip;

import java.util.List;
import java.util.Optional;

public interface TransportService {

    List<Route> findRoutes();

    Optional<Route> findRouteById(int id);

    void saveRoute(Route route);

    void deleteRoute(int routeId);

    List<Trip> findTrips(int routeId);

    Optional<Trip> findTripById(int id);

    void saveTrip(Trip trip);

    void deleteTrip(int tripId);

    List<Ticket> findTickets(int tripId);

    List<Order> findReservations(int tripId);

    void makeReservation(Order order);

    void cancelReservation(int orderId, String reason);

    void completeReservation(int orderId);

    void buyTicket(int tripId, String clientName);
}
