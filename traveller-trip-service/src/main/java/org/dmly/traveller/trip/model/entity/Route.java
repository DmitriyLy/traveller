package org.dmly.traveller.trip.model.entity;

import lombok.Setter;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "ROUTE")
@Entity
@Setter
@NamedQueries({@NamedQuery(name = Route.QUERY_FIND_ALL, query = "from Route")})
public class Route extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "Route.findAll";

    private String start;
    private String destination;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private Set<Trip> trips;

    @Column(name = "START_ID", nullable = false)
    public String getStart() {
        return start;
    }

    @Column(name = "DESTINATION_ID", nullable = false)
    public String getDestination() {
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "route", orphanRemoval = true)
    public Set<Trip> getTrips() {
        return trips;
    }

    public Trip addTrip(final Trip trip) {
        if (trips == null) {
            trips = new HashSet<>();
        }

        trips.add(trip);
        trip.setRoute(this);

        return trip;
    }
}
