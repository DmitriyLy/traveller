package org.dmly.traveller.app.infra.exception.flow;

import org.dmly.traveller.app.infra.exception.FlowException;

public class ReservationException extends FlowException {

    public ReservationException(String message) {
        super(message);
    }
}
