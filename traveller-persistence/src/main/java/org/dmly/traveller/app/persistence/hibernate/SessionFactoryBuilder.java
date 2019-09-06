package org.dmly.traveller.app.persistence.hibernate;

import org.dmly.traveller.app.infra.exception.PersistenceException;
import org.dmly.traveller.app.model.entity.geography.Address;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Coordinate;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.person.Account;
import org.dmly.traveller.app.persistence.hibernate.interceptor.TimestampInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SessionFactoryBuilder {
    private final SessionFactory sessionFactory;

    public SessionFactoryBuilder() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(loadProperties()).build();

        MetadataSources sources = new MetadataSources(registry);

        sources.addAnnotatedClass(City.class);
        sources.addAnnotatedClass(Station.class);
        sources.addAnnotatedClass(Coordinate.class);
        sources.addAnnotatedClass(Address.class);
        sources.addAnnotatedClass(Account.class);

        org.hibernate.boot.SessionFactoryBuilder builder = sources.getMetadataBuilder().build()
                .getSessionFactoryBuilder().applyInterceptor(new TimestampInterceptor());

        sessionFactory = builder.build();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @PreDestroy
    public void destroy() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private Properties loadProperties() {
        try {
            InputStream inputStream = SessionFactoryBuilder.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new PersistenceException(e.getMessage());
        }
    }
}
