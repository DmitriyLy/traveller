package org.dmly.traveller.geography.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.geography.model.search.criteria.StationCriteria;


import javax.persistence.*;
import java.util.Objects;

@Table(name = "STATION")
@Entity(name = "Station2")
@NamedQuery(name = Station.QUERY_DELETE_ALL, query = "delete from Station2")
@Setter
@EqualsAndHashCode(callSuper = true, of = { "city", "transportType", "address" })
public class Station extends AbstractEntity {
    public static final String QUERY_DELETE_ALL = "Station.deleteAll";
    public static final String FIELD_TRIP = "trip";

    private City city;
    private Address address;
    private String phone;
    private Coordinate coordinate;
    private TransportType transportType;

    public Station() {
    }

    public Station(final City city, final TransportType transportType) {
        this.city = Objects.requireNonNull(city);
        this.transportType = Objects.requireNonNull(transportType);
    }

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY_ID")
    public City getCity() {
        return city;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    @Column(name = "PHONE", length = 16)
    public String getPhone() {
        return phone;
    }

    @Embedded
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "TRANSPORT_TYPE")
    public TransportType getTransportType() {
        return transportType;
    }

    public boolean match(final StationCriteria criteria) {
        Objects.requireNonNull(criteria, "Station criteria in not initialized");

        if (!StringUtils.isEmpty(criteria.getName())) {
            if (!city.getName().equals(criteria.getName())) {
                return false;
            }
        }

        if (criteria.getTransportType() != null) {
            if (transportType != criteria.getTransportType()) {
                return false;
            }
        }

        return true;
    }
}
