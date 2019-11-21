package org.dmly.traveller.app.service.loader;

import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.model.entity.travel.Trip;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.TransportService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceEntityLoader implements EntityLoader {

    private final Map<Class<? extends AbstractEntity>, Loader> loaders;

    @Inject
    public ServiceEntityLoader(final GeographicService geographicService, final TransportService transportService) {
        loaders = new HashMap<>();

        loaders.put(Station.class, geographicService::findCityById);
        loaders.put(City.class, geographicService::findCityById);
        loaders.put(Route.class, transportService::findRouteById);
        loaders.put(Trip.class, transportService::findTripById);
    }

    @Override
    public <T extends AbstractEntity> T load(Class<T> clz, int id) {
        Loader<T> loader = loaders.get(clz);
        if (loader == null) {
            throw new ConfigurationException("No loader for class " + clz);
        }
        return loader.load(id).orElse(null);
    }

    @FunctionalInterface
    private static interface Loader<T extends AbstractEntity> {
        Optional<T> load(int id);
    }
}
