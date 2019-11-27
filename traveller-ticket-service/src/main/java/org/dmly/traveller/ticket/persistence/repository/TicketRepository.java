package org.dmly.traveller.ticket.persistence.repository;

import org.dmly.traveller.ticket.model.entity.Ticket;

import java.util.List;

public interface TicketRepository {

    void save(Ticket ticket);

    List<Ticket> findAll(int tripId);
}
