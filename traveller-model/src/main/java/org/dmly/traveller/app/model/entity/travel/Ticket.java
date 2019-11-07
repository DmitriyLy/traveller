package org.dmly.traveller.app.model.entity.travel;

import lombok.Setter;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "TICKETS")
@Setter
public class Ticket extends AbstractEntity {
    private Trip trip;
    private String name;
    private String uid;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "TRIP_ID")
    public Trip getTrip() {
        return trip;
    }

    @Column(length = 32, nullable = false)
    public String getName() {
        return name;
    }

    @Column(length = 60, nullable = false)
    public String getUid() {
        return uid;
    }
}
