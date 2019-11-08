package org.dmly.traveller.app.model.entity.travel;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.app.infra.util.generator.text.StringGenerator;
import org.dmly.traveller.app.model.entity.travel.generator.TicketNumberGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketTest {
    private Ticket ticket;

    @Before
    public void setup() {
        ticket = new Ticket();
        ticket.setId(1);
    }

    @Test
    public void generateUid_validGenerator_uidAssigned() {
        StringGenerator generator = new TicketNumberGenerator();
        ticket.generateUid(generator);

        assertNotNull(ticket.getUid());
        assertEquals(Ticket.TICKET_NUMBER_SIZE, ticket.getUid().length());
    }

    @Test(expected = InvalidParameterException.class)
    public void generateUid_nullGenerator_exceptionThrown() {
        ticket.generateUid(null);
    }
}