package org.dmly.traveller.geography.persistence.hibernate.loader;

import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;
import org.dmly.traveller.common.model.entity.loader.EntityLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

@DBSource
@Slf4j
public class SessionEntityLoader implements EntityLoader {
    private final SessionFactory sessionFactory;

    @Inject
    public SessionEntityLoader(SessionFactoryBuilder builder) {
        sessionFactory = builder.getSessionFactory();
    }

    @Override
    public <T extends AbstractEntity> T load(Class<T> clz, int id) {
        try (Session session = sessionFactory.openSession()){
            return session.get(clz, id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw  new PersistenceException(e);
        }
    }
}
