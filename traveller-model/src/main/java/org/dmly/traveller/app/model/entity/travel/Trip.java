package org.dmly.traveller.app.model.entity.travel;

import lombok.Setter;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "TRIP")
@Entity
@Setter
public class Trip extends AbstractEntity {
    private Route route;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxSeats;
    private int availableSeats;
    private double price;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID", nullable = false)
    public Route getRoute() {
        return route;
    }

    @Column(name = "START_TIME", nullable = false)
    public LocalTime getStartTime() {
        return startTime;
    }

    @Column(name = "END_TIME", nullable = false)
    public LocalTime getEndTime() {
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
