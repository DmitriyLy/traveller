package org.dmly.traveller.app.persistence.repository.hibernate.transport;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.app.persistence.repository.transport.RouteRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@DBSource
public class HibernateRouteRepository extends BaseHibernateRepository implements RouteRepository {

    @Inject
    public HibernateRouteRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public List<Route> findAll() {
        return query(session -> session.createNamedQuery(Route.QUERY_FIND_ALL, Route.class).list());
    }

    @Override
    public Optional<Route> findById(int id) {
        return query(session -> Optional.ofNullable(session.get(Route.class, id)));
    }

    @Override
    public void save(Route route) {
        execute(session -> session.saveOrUpdate(route));
    }

    @Override
    public void delete(int routeId) {
        execute(session -> {
            Route route = session.get(Route.class, routeId);
            if (route != null) {
                session.delete(route);
            }
        });
    }
}
