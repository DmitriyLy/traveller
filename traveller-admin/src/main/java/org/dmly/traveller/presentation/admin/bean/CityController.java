package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@ApplicationScoped
public class CityController {
    private final GeographicService geographicService;

    @Inject
    public CityController(GeographicService geographicService) {
        this.geographicService = geographicService;
    }

    public List<City> getCities() {
        return geographicService.findCities();
    }

    public void saveCity(CityBean cityBean) {
        City city = new City();
        city.setName(cityBean.getName());
        city.setRegion(cityBean.getRegion());
        city.setDistrict(cityBean.getDistrict());
        city.setId(cityBean.getId());
        geographicService.saveCity(city);
    }

    public void delete(int cityId) {
        geographicService.deleteCity(cityId);
    }
}
