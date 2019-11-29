package org.dmly.traveller.ticket.service.impl;

import org.dmly.traveller.common.infra.util.Checks;
import org.dmly.traveller.app.infra.util.generator.text.StringGenerator;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.ticket.model.entity.Order;
import org.dmly.traveller.ticket.model.entity.Ticket;
import org.dmly.traveller.ticket.model.generator.TicketNumberGenerator;
import org.dmly.traveller.ticket.persistence.repository.OrderRepository;
import org.dmly.traveller.ticket.persistence.repository.TicketRepository;
import org.dmly.traveller.ticket.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;

    private final OrderRepository orderRepository;

    private final StringGenerator ticketNumberGenerator = new TicketNumberGenerator();

    @Inject
    public TicketServiceImpl(@DBSource TicketRepository ticketRepository, @DBSource OrderRepository orderRepository) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Ticket> findTickets(String tripId) {
        return ticketRepository.findAll(tripId);
    }

    @Override
    public List<Order> findReservations(String tripId) {
        return orderRepository.findAll(tripId);
    }

    @Override
    public void makeReservation(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void cancelReservation(int orderId, String reason) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresentOrElse(result -> {
            result.cancel(reason);
            orderRepository.save(result);
        }, () -> LOGGER.error("Invalid order identifier: {}", orderId));
    }

    @Override
    public void completeReservation(int orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresentOrElse(resulut -> {
            resulut.complete();
            orderRepository.save(resulut);
        }, () -> LOGGER.error("Invalid order identifier: {}", orderId));
    }

    @Override
    public Ticket buyTicket(String tripId, String clientName) {
        Checks.checkParameter(tripId != null, "Trip identifier should be not null");

        Ticket ticket = new Ticket();
        ticket.setTripId(tripId);
        ticket.generateUid(ticketNumberGenerator);
        ticket.setName(clientName);
        ticketRepository.save(ticket);

        return ticket;
    }
}
