package org.dmly.traveller.app.config;

import org.dmly.traveller.app.infra.cdi.DBSourceInstance;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.StationRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateStationRepository;
import org.dmly.traveller.app.persistence.repository.inmemory.InMemoryCityRepository;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.impl.GeographicServiceImpl;
import org.dmly.traveller.app.service.transform.Transformer;
import org.dmly.traveller.app.service.transform.impl.SimpleDTOTransformer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(HibernateCityRepository.class).to(CityRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateStationRepository.class).to(StationRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(SimpleDTOTransformer.class).to(Transformer.class).in(Singleton.class);
        bind(GeographicServiceImpl.class).to(GeographicService.class).in(Singleton.class);
        bind(SessionFactoryBuilder.class).to(SessionFactoryBuilder.class).in(Singleton.class);
    }
}