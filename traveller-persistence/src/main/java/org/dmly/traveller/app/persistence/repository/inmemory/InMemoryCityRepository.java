package org.dmly.traveller.app.persistence.repository.inmemory;

import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.persistence.repository.CityRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCityRepository implements CityRepository {
    private final List<City> cities;
    private int counter = 0;
    private int stationCounter = 0;

    public InMemoryCityRepository() {
        cities = new ArrayList<>();
    }

    @Override
    public void save(final City city) {
        if (!cities.contains(city)) {
            city.setId(++counter);
            cities.add(city);
        }

        city.getStations().forEach( station -> {
            if (station.getId() == 0) {
                station.setId(++stationCounter);
            }
        });
    }

    @Override
    public City findById(int cityId) {
        return cities.stream()
                .filter(city -> city.getId() == cityId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(int cityId) {

    }

    @Override
    public List<City> findAll() {
        return CommonUtil.getSafeList(cities);
    }

    @Override
    public void deleteAll() {
        cities.clear();
    }

    @Override
    public void saveAll(List<City> cities) {
        cities.forEach(this::save);
    }
}
