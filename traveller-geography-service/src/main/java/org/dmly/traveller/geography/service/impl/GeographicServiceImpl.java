package org.dmly.traveller.geography.service.impl;

import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.geography.model.entity.City;
import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.search.criteria.StationCriteria;
import org.dmly.traveller.geography.persistence.repository.CityRepository;
import org.dmly.traveller.geography.persistence.repository.StationRepository;
import org.dmly.traveller.geography.service.GeographicService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of the {@link GeographicService}
 *
 */
@Named
public class GeographicServiceImpl implements GeographicService {
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;

    @Inject
    public GeographicServiceImpl(@DBSource CityRepository cityRepository, @DBSource StationRepository stationRepository) {
        this.cityRepository = cityRepository;
        this.stationRepository = stationRepository;
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
       return stationRepository.findByCriteria(criteria);
    }

    @Override
    public void saveCity(City city) {
        cityRepository.save(city);
    }

    @Override
    public void saveCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    @Override
    public void deleteCities() {
        cityRepository.deleteAll();
    }

    @Override
    public void deleteCity(int id) {
        cityRepository.delete(id);
    }

    @Override
    public Optional<Station> findStationById(int id) {
        return Optional.ofNullable(stationRepository.findById(id));
    }

    @Override
    public void saveStation(Station station) {
        stationRepository.save(station);
    }
}
