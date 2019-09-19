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
public class HibernateCityRepository extends BaseHibernateRepository implements CityRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateCityRepository.class);

    @Inject
    public HibernateCityRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public void save(City city) {
        execute(session -> session.saveOrUpdate(city));
    }

    @Override
    public City findById(int cityId) {
        return query(session -> session.get(City.class, cityId));
    }

    @Override
    public void delete(int cityId) {
        execute(session -> {
            City city = session.get(City.class, cityId);
            if (city != null) {
                session.delete(city);
            }
        });
    }

    @Override
    public List<City> findAll() {
        return query(session -> session.createNamedQuery(City.));
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
