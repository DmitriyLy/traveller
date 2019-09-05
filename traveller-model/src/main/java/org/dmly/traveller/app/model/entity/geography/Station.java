package org.dmly.traveller.app.model.entity.geography;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.transport.TransportType;

import java.util.Objects;

public class Station extends AbstractEntity {
    private City city;
    private Address address;
    private String phone;
    private Coordinate coordinate;
    private TransportType transportType;

    public Station(final City city, final TransportType transportType) {
        this.city = Objects.requireNonNull(city);
        this.transportType = Objects.requireNonNull(transportType);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Station station = (Station) o;
        return Objects.equals(city, station.city) &&
                Objects.equals(address, station.address) &&
                Objects.equals(phone, station.phone) &&
                transportType == station.transportType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), city, address);
    }
}
