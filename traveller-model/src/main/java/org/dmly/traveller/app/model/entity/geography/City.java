package org.dmly.traveller.app.model.entity.geography;

import lombok.Setter;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.transport.TransportType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Any locality that contains transport stations
 *
 */
@Table(name = "CITY")
@Entity
@NamedQueries({
        @NamedQuery(name = City.QUERY_DELETE_ALL, query = "delete from City"),
        @NamedQuery(name = City.QUERY_FIND_ALL, query = "from City")
})
@Setter
public class City extends AbstractEntity {
    public static final String QUERY_DELETE_ALL = "deleteCities";
    public static final String QUERY_FIND_ALL = "City.findAll";

    private String name;
    private String district;
    private String region;
    private Set<Station> stations;

    public City() {
    }

    public City(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = 2, max = 32)
    @Column(name = "NAME", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    @NotNull
    @Size(min = 2, max = 32)
    @Column(name = "DISTRICT", nullable = false, length = 32)
    public String getDistrict() {
        return district;
    }

    @NotNull
    @Size(min = 2, max = 32)
    @Column(name = "REGION", nullable = false, length = 32)
    public String getRegion() {
        return region;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "city", orphanRemoval = true)
    public Set<Station> getStations() {
        return CommonUtil.getSafeSet(stations);
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
