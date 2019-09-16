package org.dmly.traveller.app.persistence.repository;

import org.dmly.traveller.app.model.entity.geography.City;

import java.util.List;

public interface CityRepository {
    
    void save(City city);
    City findById(int cityId);
    void delete(int cityId);
    List<City> findAll();
    void deleteAll();
}
