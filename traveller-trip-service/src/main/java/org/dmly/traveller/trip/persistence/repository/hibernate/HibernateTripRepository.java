package org.dmly.traveller.trip.persistence.repository.hibernate;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.trip.model.entity.Trip;
import org.dmly.traveller.trip.persistence.repository.TripRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@DBSource
public class HibernateTripRepository extends BaseHibernateRepository implements TripRepository {

    @Inject
    public HibernateTripRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public List<Trip> findAll(int routeId) {
        return query(session -> {
            Criteria criteria = session.createCriteria(Trip.class);

            criteria = criteria.createCriteria(Trip.FIELD_ROUTE);
            criteria.add(Restrictions.eq(AbstractEntity.FIELD_ID, routeId));

            return criteria.list();
        });
    }

    @Override
    public Optional<Trip> findById(int id) {
        return query(session -> Optional.ofNullable(session.get(Trip.class, id)));
    }

    @Override
    public void save(Trip trip) {
        execute(session -> session.saveOrUpdate(trip));
    }

    @Override
    public void delete(int tripId) {
        execute(session -> {
            Trip trip = session.get(Trip.class, tripId);
            if (trip != null) {
                session.delete(trip);
            }
        });
    }
}
