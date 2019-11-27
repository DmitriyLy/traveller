package org.dmly.traveller.geography.persistence.repository;

import org.dmly.traveller.geography.model.entity.City;

import java.util.List;

public interface CityRepository {
    
    void save(City city);
    City findById(int cityId);
    void delete(int cityId);
    List<City> findAll();
    void deleteAll();
    void saveAll(List<City> cities);
}
