package org.dmly.traveller.ticket.service;

import org.dmly.traveller.ticket.model.entity.Order;
import org.dmly.traveller.ticket.model.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findTickets(String tripId);

    List<Order> findReservations(String tripId);

    void makeReservation(Order order);

    void cancelReservation(int orderId, String reason);

    void completeReservation(int orderId);

    Ticket buyTicket(String tripId, String clientName);
}
