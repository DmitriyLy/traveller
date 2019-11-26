package org.dmly.traveller.ticket.model.entity;

import lombok.Setter;
import org.dmly.traveller.app.infra.exception.flow.ReservationException;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS")
@Setter
public class Order extends AbstractEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(Order.class);

    private OrderState state;
    private LocalDateTime dueDate;
    //private Trip trip;
    private Ticket ticket;
    private String clientName;
    private String clientPhone;
    private String cancellationReason;

    @Enumerated()
    @Column(name = "ORDER_STATE", nullable = false)
    public OrderState getState() {
        return state;
    }

    @Column(name = "DUE_DATE", nullable = false)
    public LocalDateTime getDueDate() {
        return dueDate;
    }

   /* @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "TRIP_ID", nullable = false)
    public Trip getTrip() {
        return trip;
    }*/

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_ID")
    public Ticket getTicket() {
        return ticket;
    }

    @Column(name = "CLIENT_NAME", length = 32, nullable = false)
    public String getClientName() {
        return clientName;
    }

    @Column(name = "CLIENT_PHONE", nullable = false, length = 24)
    public String getClientPhone() {
        return clientPhone;
    }

    @Column(name = "CANCELLATION_REASON", length = 128)
    public String getCancellationReason() {
        return cancellationReason;
    }

    public void cancel(String reason) {
        if (dueDate.isBefore(LocalDateTime.now())) {
            LOGGER.warn("This order misses due date and should be automatically cancelled, id: " + getId());
        }
        this.state = OrderState.CANCELLED;
        this.cancellationReason = reason;
    }

    public void complete() {
        if (dueDate.isBefore(LocalDateTime.now())) {
            throw new ReservationException("This order misses due date, id: " + getId());
        }

        this.state = OrderState.COMPLETED;
    }

    @Transient
    public boolean isCompleted() {
        return state == OrderState.COMPLETED;
    }

    @Transient
    public boolean isCancelled() {
        return state == OrderState.CANCELLED;
    }

}
