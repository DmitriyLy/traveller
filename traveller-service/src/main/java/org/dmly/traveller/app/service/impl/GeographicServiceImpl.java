package org.dmly.traveller.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.inmemory.InMemoryCityRepository;
import org.dmly.traveller.app.service.GeographicService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Default implementation of the {@link GeographicService}
 *
 */
public class GeographicServiceImpl implements GeographicService {
    private final CityRepository cityRepository;

    public GeographicServiceImpl() {
        cityRepository = new InMemoryCityRepository();
    }

    @Override
    public List<City> findCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findCityById(int id) {
        return Optional.ofNullable(cityRepository.findById(id));
    }

    @Override
    public List<Station> searchStations(StationCriteria criteria, RangeCriteria rangeCriteria) {
       Set<Station> stations = new HashSet<>();
       cityRepository.findAll().forEach(city -> stations.addAll(city.getStations()));
       return stations.stream().filter(station -> station.match(criteria)).collect(Collectors.toList());
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }
}
