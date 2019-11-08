package org.dmly.traveller.app.persistence.repository.transport;

import org.dmly.traveller.app.model.entity.travel.Ticket;

import java.util.List;

public interface TicketRepository {

    void save(Ticket ticket);

    List<Ticket> findAll(int tripId);
}
