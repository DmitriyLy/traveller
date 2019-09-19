package org.dmly.traveller.app.persistence.repository.hibernate;

import javax.persistence.PersistenceException;

import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseHibernateRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHibernateRepository.class);

    private final SessionFactory sessionFactory;

    public BaseHibernateRepository(SessionFactoryBuilder builder) {
        sessionFactory = builder.getSessionFactory();
    }

    protected int getBatchSize() {
        return sessionFactory.getSessionFactoryOptions().getJdbcBatchSize();
    }

    protected void execute(Consumer<Session> action) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (Exception e) {
            handleError(transaction, e);
            throw new PersistenceException(e);
        }
    }

    protected <R> R query(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()){
            return function.apply(session);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new PersistenceException(e);
        }
    }

    private void handleError(Transaction transaction, Exception e) {
        LOGGER.error(e.getMessage(), e);
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
