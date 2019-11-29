package org.dmly.traveller.ticket.model.entity;

import lombok.Setter;
import org.dmly.traveller.common.infra.util.Checks;
import org.dmly.traveller.app.infra.util.generator.text.StringGenerator;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "TICKETS")
@Setter
@NamedQueries({@NamedQuery(name = Ticket.QUERY_FIND_ALL, query = "from Ticket")})
public class Ticket extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "Ticket.findAll";
    public static final int TICKET_NUMBER_SIZE = 32;

    private String tripId;
    private String name;
    private String uid;

    @Column(name = "TRIP_ID", nullable = false)
    public String getTripId() {
        return tripId;
    }

    @Column(length = 32, nullable = false)
    public String getName() {
        return name;
    }

    @Column(length = 32, nullable = false)
    public String getUid() {
        return uid;
    }

    public void generateUid(final StringGenerator stringGenerator) {
        Checks.checkParameter(stringGenerator != null, "StringGenerator should be initialized");
        uid = stringGenerator.generate();
    }
}
