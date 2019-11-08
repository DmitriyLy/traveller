package org.dmly.traveller.app.persistence.repository.hibernate.transport;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.model.entity.travel.Ticket;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.app.persistence.repository.transport.TicketRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@DBSource
public class HibernateTicketRepository extends BaseHibernateRepository implements TicketRepository {

    @Inject
    public HibernateTicketRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public void save(Ticket ticket) {
        execute(session -> session.saveOrUpdate(ticket));
    }

    @Override
    public List<Ticket> findAll(int tripId) {
        return query(session -> session.createNamedQuery(Ticket.QUERY_FIND_ALL, Ticket.class).list());
    }
}
