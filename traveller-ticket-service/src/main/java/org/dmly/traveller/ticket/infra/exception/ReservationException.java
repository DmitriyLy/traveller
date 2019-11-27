package org.dmly.traveller.ticket.infra.exception;

import org.dmly.traveller.app.infra.exception.FlowException;

public class ReservationException extends FlowException {

    public ReservationException(String message) {
        super(message);
    }
}
