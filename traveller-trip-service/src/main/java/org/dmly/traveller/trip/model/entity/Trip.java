package org.dmly.traveller.trip.model.entity;

import lombok.Setter;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "TRIP")
@Entity
@Setter
public class Trip extends AbstractEntity {
    public static final String FIELD_ROUTE = "route";

    private Route route;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int maxSeats;
    private int availableSeats;
    private double price;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID", nullable = false)
    public Route getRoute() {
        return route;
    }

    @Column(name = "START_TIME", nullable = false)
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Column(name = "END_TIME", nullable = false)
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Column(name = "MAX_SEATS", nullable = false)
    public int getMaxSeats() {
        return maxSeats;
    }

    @Column(name = "AVAILABLE_SEATS", nullable = false)
    public int getAvailableSeats() {
        return availableSeats;
    }

    @Column(name = "PRICE", nullable = false)
    public double getPrice() {
        return price;
    }
}
