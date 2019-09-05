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
       Set<Station> stations = new HashSet<>();
       for (City city : cities) {
           stations.addAll(city.getStations());
       }

       return stations.stream().filter(station -> station.match(criteria)).collect(Collectors.toList());
    }

    @Override
    public void saveCity(City city) {
        if(!cities.contains(city)) {
            cities.add(city);
        }
    }
}
