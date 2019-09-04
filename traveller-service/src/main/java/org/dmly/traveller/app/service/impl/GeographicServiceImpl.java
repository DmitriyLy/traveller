package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.infra.util.CommonUtil;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;

import java.util.ArrayList;
import java.util.List;

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
    public void saveCity(City city) {
        if(!cities.contains(city)) {
            cities.add(city);
        }
    }
}
