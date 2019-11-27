package org.dmly.traveller.app.persistence.hibernate;

import org.dmly.traveller.app.infra.environment.Environment;
import org.dmly.traveller.app.infra.exception.PersistenceException;
import org.dmly.traveller.app.persistence.hibernate.interceptor.TimestampInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Entity;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@Named
public class SessionFactoryBuilder {
    private static final String PREFIX_PROPERTIES = "hibernate.";

    private final SessionFactory sessionFactory;

    @Inject
    public SessionFactoryBuilder(Environment environment) {
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(environment.getProperties(PREFIX_PROPERTIES)).build();

        MetadataSources sources = new MetadataSources(registry);

        String basePackage = environment.getProperty("hibernate.base.package", "org.dmly.traveller");
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);

        entityClasses.forEach(sources::addAnnotatedClass);

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
}
