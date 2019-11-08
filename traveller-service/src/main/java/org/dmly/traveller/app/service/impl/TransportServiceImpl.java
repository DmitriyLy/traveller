package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.model.entity.travel.Order;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.model.entity.travel.Ticket;
import org.dmly.traveller.app.model.entity.travel.Trip;
import org.dmly.traveller.app.persistence.repository.transport.OrderRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;
import org.dmly.traveller.app.persistence.repository.transport.TicketRepository;
import org.dmly.traveller.app.persistence.repository.transport.TripRepository;
import org.dmly.traveller.app.service.TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TransportServiceImpl implements TransportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransportServiceImpl.class);

    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final TripRepository tripRepository;
    private final OrderRepository orderRepository;

    @Inject
    public TransportServiceImpl(@DBSource RouteRepository routeRepository, @DBSource TicketRepository ticketRepository,
                                @DBSource TripRepository tripRepository, @DBSource OrderRepository orderRepository) {
        this.routeRepository = routeRepository;
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
        this.orderRepository = orderRepository;
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

    @Override
    public List<Ticket> findTickets(int tripId) {
        return ticketRepository.findAll(tripId);
    }

    @Override
    public List<Order> findReservations(int tripId) {
        return orderRepository.findAll(tripId);
    }

    @Override
    public void makeReservation(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void cancelReservation(int orderId, String reason) {
        Optional<Order> order = orderRepository.findById(orderId);

        order.ifPresentOrElse(
                result -> {
                    result.cancel(reason);
                    orderRepository.save(result);
                },
                () -> LOGGER.error("Invalid order identifier: {}", orderId)
        );
    }

    @Override
    public void completeReservation(int orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        order.ifPresentOrElse(
                result -> {
                    result.complete();
                    orderRepository.save(result);
                },
                () -> LOGGER.error("Invalid order identifier: {}", orderId)
        );
    }

    @Override
    public void buyTicket(int tripId, String clientName) {
        Optional<Trip> trip = tripRepository.findById(tripId);

        trip.ifPresentOrElse(
                data -> {
                    Ticket ticket = new Ticket();
                    ticket.setTrip(data);
                    ticket.setUid(String.valueOf(System.nanoTime()));
                    ticket.setName(clientName);
                    ticketRepository.save(ticket);
                },
                () -> LOGGER.error("Invalid trip identifier: {}", tripId)
        );
    }
}
