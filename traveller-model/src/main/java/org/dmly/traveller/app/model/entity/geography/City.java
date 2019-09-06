package org.dmly.traveller.app.model.entity.geography;

import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.transport.TransportType;

import javax.persistence.*;
import java.util.*;

/**
 * Any locality that contains transport stations
 *
 */
@Table(name = "CITY")
@Entity
public class City extends AbstractEntity {
    private String name;
    private String district;
    private String region;
    private Set<Station> stations;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    @Column(name = "NAME", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Column(name = "REGION", nullable = false, length = 32)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city", orphanRemoval = true)
    public Set<Station> getStations() {
        return CommonUtil.getSafeSet(stations);
    }

    public void setStations(Set<Station> stations) {
        this.stations = stations;
    }

    /**
     * Adds specified station to the city station list
     * @param transportType
     */
    public Station addStation(final TransportType transportType) {
        if(stations == null) {
            stations = new HashSet<>();
        }
        Station station = new Station(this, transportType);
        stations.add(station);

        return station;
    }

    /**
     * Removes specified station from city station list
     * @param station
     */
    public void removeStation(Station station) {
        Objects.requireNonNull(station, "station parameter is not initialized");
        if(stations == null) {
            return;
        }
        stations.remove(station);
    }
}
