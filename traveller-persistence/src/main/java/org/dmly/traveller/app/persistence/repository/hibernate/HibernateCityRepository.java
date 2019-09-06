package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import java.util.List;

public class HibernateCityRepository implements CityRepository {
    private final SessionFactory sessionFactory;

    @Inject
    public HibernateCityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(City city) {
        try (Session session = sessionFactory.openSession()) {
            session.saveOrUpdate(city);
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
