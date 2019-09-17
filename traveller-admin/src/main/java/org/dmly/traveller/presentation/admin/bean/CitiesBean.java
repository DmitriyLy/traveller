package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@RequestScoped
public class CitiesBean {
    private final GeographicService geographicService;

    @Inject
    public CitiesBean(GeographicService geographicService) {
        this.geographicService = geographicService;
        City testCity = new City();
        testCity.setName("Odessa TEST");
        testCity.setRegion("Odessa TEST");
        testCity.setDistrict("Odessa TEST");
        this.geographicService.saveCity(testCity);
    }

    public List<City> getCities() {
        return geographicService.findCities();
    }
}
