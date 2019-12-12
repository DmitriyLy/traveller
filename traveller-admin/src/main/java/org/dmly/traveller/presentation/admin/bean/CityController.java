package org.dmly.traveller.presentation.admin.bean;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.monitoring.MetricsManager;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.common.model.transform.Transformer;
import org.dmly.traveller.geography.dto.CityDTO;
import org.dmly.traveller.presentation.admin.client.CityClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;


@Named
@ApplicationScoped
@Slf4j
public class CityController {
    private final GeographicService geographicService;

    private final Transformer transformer;

    private final MetricsManager metricsManager;

    private final CityClient cityClient;

    private Counter savedCitiesCounter;

    @Inject
    @Push
    private PushContext cityChannel;

    @Inject
    public CityController(GeographicService geographicService, Transformer transformer, MetricsManager metricsManager,
                          CityClient cityClient) {
        this.geographicService = geographicService;
        this.transformer = transformer;
        this.metricsManager = metricsManager;
        this.cityClient = cityClient;
    }

    public List<City> getCities() {
        return geographicService.findCities();
    }

    public void saveCity(CityBean cityBean) {
        CityDTO cityDTO = transformer.untransform(cityBean, CityDTO.class);
        cityClient.create(cityDTO);

        savedCitiesCounter.inc();
        log.info("Saved a city {}", cityBean);
    }

    public void update(City city, CityBean cityBean) {
        //transformer.transform(city, cityBean);
    }

    public void delete(int cityId) {
        //geographicService.deleteCity(cityId);
    }

    @PostConstruct
    public void init() {
        savedCitiesCounter = (Counter) metricsManager.registerMetric(MetricRegistry.name("city", "saved"),
                new Counter());
    }
}
