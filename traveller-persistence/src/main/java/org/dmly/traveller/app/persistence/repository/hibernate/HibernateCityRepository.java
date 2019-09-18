package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@DBSource
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

    @Override
    public void deleteAll() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                Query stationQuery = session.getNamedQuery(Station.QUERY_DELETE_ALL);
                stationQuery.executeUpdate();
                Query cityQuery = session.getNamedQuery(City.QUERY_DELETE_ALL);
                int deleted = cityQuery.executeUpdate();
                LOGGER.debug("Deleted {} cities", deleted);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void saveAll(List<City> cities) {
        int batchSize = sessionFactory.getSessionFactoryOptions().getJdbcBatchSize();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                for (int i = 0; i < cities.size(); i++) {
                    session.persist(cities.get(i));
                    if (i % batchSize == 0 || i == cities.size() - 1) {
                        session.flush();
                        session.clear();
                    }
                }

                transaction.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                if (transaction != null) {
                    transaction.rollback();
                }
            }
        }
    }
}
