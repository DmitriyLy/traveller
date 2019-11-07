package org.dmly.traveller.app.model.entity.travel;

import lombok.Setter;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.geography.Station;

import javax.persistence.*;
import java.time.LocalTime;

@Table(name = "ROUTE")
@Entity
@Setter
public class Route extends AbstractEntity {
    private Station start;
    private Station destination;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "START_ID")
    public Station getStart() {
        return start;
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "DESTINATION_ID")
    public Station getDestination() {
        return destination;
    }

    @Column(name = "START_TIME", nullable = false)
    public LocalTime getStartTime() {
        return startTime;
    }

    @Column(name = "END_TIME", nullable = false)
    public LocalTime getEndTime() {
        return endTime;
    }

    @Column(name = "PRICE")
    public double getPrice() {
        return price;
    }
}
