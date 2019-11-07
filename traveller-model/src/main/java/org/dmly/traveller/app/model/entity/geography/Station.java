package org.dmly.traveller.app.model.entity.geography;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "STATION")
@Entity
@NamedQuery(name = Station.QUERY_DELETE_ALL, query = "delete from Station")
@Setter
public class Station extends AbstractEntity {
    public static final String QUERY_DELETE_ALL = "deleteStations";

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((transportType == null) ? 0 : transportType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Station other = (Station) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (transportType != other.transportType)
            return false;
        return true;
    }
}
