package org.dmly.traveller.app.model.entity.travel;

import lombok.Setter;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.geography.Station;

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

    private Station start;
    private Station destination;
    private LocalTime startTime;
    private LocalTime endTime;
    private double price;
    private Set<Trip> trips;

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
