package org.dmly.traveller.ticket.model.generator;

import org.dmly.traveller.app.infra.util.generator.text.RandomDigitStringGenerator;
import org.dmly.traveller.ticket.model.entity.Ticket;


public class TicketNumberGenerator extends RandomDigitStringGenerator {
    public TicketNumberGenerator() {
        super(Ticket.TICKET_NUMBER_SIZE);
    }
}
