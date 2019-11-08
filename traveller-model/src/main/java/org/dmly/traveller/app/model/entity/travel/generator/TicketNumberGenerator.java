package org.dmly.traveller.app.model.entity.travel.generator;

import org.dmly.traveller.app.infra.util.generator.text.RandomDigitStringGenerator;
import org.dmly.traveller.app.model.entity.travel.Ticket;

public class TicketNumberGenerator extends RandomDigitStringGenerator {
    public TicketNumberGenerator() {
        super(Ticket.TICKET_NUMBER_SIZE);
    }
}
