package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@ApplicationScoped
public class CityController {
    private final GeographicService geographicService;
    private final Transformer transformer;

    @Inject
    @Push
    private PushContext cityChannel;

    @Inject
    public CityController(GeographicService geographicService, Transformer transformer) {
        this.geographicService = geographicService;
        this.transformer = transformer;
    }

    public List<City> getCities() {
        return geographicService.findCities();
    }

    public void saveCity(CityBean cityBean) {
        City city = transformer.untransform(cityBean, City.class);
        geographicService.saveCity(city);

        cityChannel.send("test");
    }

    public void update(City city, CityBean cityBean) {
        transformer.transform(city, cityBean);
    }

    public void delete(int cityId) {
        geographicService.deleteCity(cityId);
    }
}
