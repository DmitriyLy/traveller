package org.dmly.traveller.geography.service;

import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.geography.model.entity.City;
import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.search.criteria.StationCriteria;

import java.util.List;
import java.util.Optional;

/**
 * Entry point to perform operations
 * over geographic entities
 * @author Morenets
 *
 */
public interface GeographicService {

    List<City> findCities();

    Optional<City> findCityById(int id);

    List<Station> searchStations(StationCriteria criteria, RangeCriteria rangeCriteria);

    Optional<Station> findStationById(int id);

    void saveStation(Station station);

    void saveCity(City city);

    void saveCities(List<City> cities);

    void deleteCities();

    void deleteCity(int id);
}
