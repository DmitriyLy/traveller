package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@ApplicationScoped
public class CityController {
    private final GeographicService geographicService;
    private final Transformer transformer;

    @Inject
    public CityController(GeographicService geographicService, Transformer transformer) {
        this.geographicService = geographicService;
        this.transformer = transformer;
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

    public void update(City city, CityBean cityBean) {
        transformer.transform(city, cityBean);
    }

    public void delete(int cityId) {
        geographicService.deleteCity(cityId);
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object event) {
    }
}
