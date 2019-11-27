package org.dmly.traveller.ticket.service.impl;

import org.dmly.traveller.app.infra.util.generator.text.StringGenerator;
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

public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final TicketRepository ticketRepository;

    private final OrderRepository orderRepository;

    private final StringGenerator ticketNumberGenerator = new TicketNumberGenerator();

    @Inject
    public TicketServiceImpl(TicketRepository ticketRepository, OrderRepository orderRepository) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public List<Ticket> findTickets(String tripId) {
        return null;
    }

    @Override
    public List<Order> findReservations(String tripId) {
        return null;
    }

    @Override
    public void makeReservation(Order order) {

    }

    @Override
    public void cancelReservation(int orderId, String reason) {

    }

    @Override
    public void completeReservation(int orderId) {

    }

    @Override
    public Ticket buyTicket(String tripId, String clientName) {
        return null;
    }
}
