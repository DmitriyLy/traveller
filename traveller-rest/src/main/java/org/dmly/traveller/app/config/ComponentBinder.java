package org.dmly.traveller.app.config;

import org.dmly.traveller.app.infra.cdi.CachedInstance;
import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.infra.environment.StandardPropertyEnvironment;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.persistence.hibernate.loader.SessionEntityLoader;
import org.dmly.traveller.app.persistence.repository.hibernate.transport.HibernateOrderRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.transport.HibernateRouteRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.transport.HibernateTicketRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.transport.HibernateTripRepository;
import org.dmly.traveller.app.persistence.repository.transport.OrderRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;
import org.dmly.traveller.app.persistence.repository.transport.TicketRepository;
import org.dmly.traveller.app.persistence.repository.transport.TripRepository;
import org.dmly.traveller.app.service.TransportService;
import org.dmly.traveller.app.service.impl.TransportServiceImpl;
import org.dmly.traveller.app.service.transform.impl.CachedFieldProvider;
import org.dmly.traveller.app.service.transform.impl.EntityReferenceTransformer;
import org.dmly.traveller.app.service.transform.impl.FieldProvider;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.dmly.traveller.app.infra.cdi.DBSourceInstance;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.StationRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateStationRepository;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.impl.GeographicServiceImpl;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.inject.Singleton;

public class ComponentBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(HibernateCityRepository.class).to(CityRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateStationRepository.class).to(StationRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateOrderRepository.class).to(OrderRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateTicketRepository.class).to(TicketRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateRouteRepository.class).to(RouteRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(HibernateTripRepository.class).to(TripRepository.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(EntityReferenceTransformer.class).to(Transformer.class).in(Singleton.class);
        bind(SessionEntityLoader.class).to(EntityLoader.class).in(Singleton.class).qualifiedBy(new DBSourceInstance());
        bind(CachedFieldProvider.class).to(FieldProvider.class).in(Singleton.class).qualifiedBy(new CachedInstance());
        bind(GeographicServiceImpl.class).to(GeographicService.class).in(Singleton.class);
        bind(TransportServiceImpl.class).to(TransportService.class).in(Singleton.class);
        bind(SessionFactoryBuilder.class).to(SessionFactoryBuilder.class).in(Singleton.class);
        bind(StandardPropertyEnvironment.class).to(Environment.class).in(Singleton.class);
    }
}
