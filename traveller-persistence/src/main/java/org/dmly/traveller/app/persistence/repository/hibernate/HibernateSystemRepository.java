package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.infra.exception.PersistenceException;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.SystemRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;

@Named
@DBSource
public class HibernateSystemRepository extends BaseHibernateRepository implements SystemRepository {

    @Inject
    public HibernateSystemRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public void healthCheck() throws PersistenceException {
        execute(session -> {
            Query query = session.createNativeQuery("SELECT version();");
            query.getSingleResult();
        });
    }
}
