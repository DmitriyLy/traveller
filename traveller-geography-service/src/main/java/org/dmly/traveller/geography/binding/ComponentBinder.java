package org.dmly.traveller.geography.binding;


import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.common.model.entity.loader.EntityLoader;
import org.dmly.traveller.common.model.transform.Transformer;
import org.dmly.traveller.common.model.transform.impl.CachedFieldProvider;
import org.dmly.traveller.common.model.transform.impl.EntityReferenceTransformer;
import org.dmly.traveller.common.model.transform.impl.FieldProvider;
import org.dmly.traveller.geography.infra.cdi.CachedInstance;
import org.dmly.traveller.geography.infra.cdi.DBSourceInstance;
import org.dmly.traveller.geography.persistence.hibernate.loader.SessionEntityLoader;
import org.dmly.traveller.geography.persistence.repository.CityRepository;
import org.dmly.traveller.geography.persistence.repository.StationRepository;
import org.dmly.traveller.geography.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.geography.persistence.repository.hibernate.HibernateStationRepository;
import org.dmly.traveller.geography.service.GeographicService;
import org.dmly.traveller.geography.service.impl.GeographicServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import javax.inject.Singleton;

public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(HibernateCityRepository.class).to(CityRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateStationRepository.class).to(StationRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(EntityReferenceTransformer.class).to(Transformer.class).in(Singleton.class);
        bind(SessionEntityLoader.class).to(EntityLoader.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(CachedFieldProvider.class).to(FieldProvider.class).in(Singleton.class).qualifiedBy(new CachedInstance());
        bind(GeographicServiceImpl.class).to(GeographicService.class).in(Singleton.class);
        bind(SessionFactoryBuilder.class).to(SessionFactoryBuilder.class).in(Singleton.class);
        bind(StandardPropertyEnvironment.class).to(Environment.class).in(Singleton.class);
    }
}
