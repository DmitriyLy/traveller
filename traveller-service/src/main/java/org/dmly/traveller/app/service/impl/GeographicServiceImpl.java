package org.dmly.traveller.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.service.GeographicService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link GeographicService}
 *
 */
public class GeographicServiceImpl implements GeographicService {
    private final List<City> cities;

    public GeographicServiceImpl() {
        this.cities = new ArrayList<>();
    }

    @Override
    public List<City> findCities() {
        return CommonUtil.getSafeList(cities);
    }

    @Override
    public Optional<City> findCityById(int id) {
        return cities.stream().filter(city -> city.getId() == id).findFirst();
    }

    @Override
    public List<Station> searchStations(StationCriteria criteria, RangeCriteria rangeCriteria) {
        Stream<City> stream = cities.stream().filter(
                city -> StringUtils.isEmpty(criteria.getName()) || city.getName().equals(criteria.getName())
        );

        Optional<Set<Station>> stations = stream
                .map(city -> city.getStations())
                .reduce(((stations1, stations2) -> {
                    Set<Station> newStation = new HashSet<>(stations2);
                    newStation.addAll(stations1);
                    return newStation;
                }));

        if (!stations.isPresent()) {
            return Collections.emptyList();
        }

        return stations.get()
                .stream()
                .filter(station -> criteria.getTransportType() == null || station.getTransportType() == criteria.getTransportType())
                .collect(Collectors.toList());
    }

    @Override
    public void saveCity(City city) {
        if(!cities.contains(city)) {
            cities.add(city);
        }
    }
}
