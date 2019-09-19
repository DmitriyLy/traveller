package org.dmly.traveller.app.persistence.hibernate;

import org.dmly.traveller.app.infra.exception.PersistenceException;
import org.dmly.traveller.app.persistence.hibernate.interceptor.TimestampInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.persistence.Entity;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

@Named
public class SessionFactoryBuilder {
    private final SessionFactory sessionFactory;

    public SessionFactoryBuilder() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(loadProperties()).build();

        MetadataSources sources = new MetadataSources(registry);

        Reflections reflections = new Reflections("org.dmly.traveller.app.model.entity");
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
