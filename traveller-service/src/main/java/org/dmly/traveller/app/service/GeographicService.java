package org.dmly.traveller.app.service;

import org.dmly.traveller.app.model.entity.geography.City;

import java.util.List;

/**
 * Entry point to perform operations
 * over geographic entities
 * @author Morenets
 *
 */
public interface GeographicService {

    List<City> findCities();

    void saveCity(City city);
}
