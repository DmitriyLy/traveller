package org.dmly.traveller.app.service;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;

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

    void saveCity(City city);

    void saveCities(List<City> cities);

    void deleteCities();

    void deleteCity(int id);
}
