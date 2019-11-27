package org.dmly.traveller.ticket.persistence.repository.hibernate;

import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.ticket.model.entity.Order;
import org.dmly.traveller.ticket.persistence.repository.OrderRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@DBSource
public class HibernateOrderRepository extends BaseHibernateRepository implements OrderRepository {

    @Inject
    public HibernateOrderRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public void save(Order order) {
        execute(session -> session.saveOrUpdate(order));
    }

    @Override
    public List<Order> findAll(String tripId) {
        return query(session ->  {
            Criteria criteria = session.createCriteria(Order.class);

            criteria.add(Restrictions.eq(Order.FIELD_TRIP_ID, tripId));
            return criteria.list();
        });
    }

    @Override
    public Optional<Order> findById(int id) {
        return Optional.ofNullable(query(session -> session.get(Order.class, id)));
    }
}
