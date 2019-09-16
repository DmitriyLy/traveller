package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class HibernateCityRepository implements CityRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateCityRepository.class);

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateCityRepository(SessionFactoryBuilder builder) {
        this.sessionFactory = builder.getSessionFactory();
    }

    @Override
    public void save(City city) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(city);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public City findById(int cityId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(City.class, cityId);
        }
    }

    @Override
    public void delete(int cityId) {
        try (Session session = sessionFactory.openSession()) {
            City city = session.get(City.class, cityId);
            if (city != null) {
                session.delete(city);
            }
        }
    }

    @Override
    public List<City> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createCriteria(City.class).list();
        }
    }
}
