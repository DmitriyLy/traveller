package org.dmly.traveller.user.persistence.repository.hibernate;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.user.model.entity.User;
import org.dmly.traveller.user.persistence.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@DBSource
public class HibernateUserRepository extends BaseHibernateRepository implements UserRepository {

    @Inject
    public HibernateUserRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public void save(User user) {
        execute(session -> session.saveOrUpdate(user));
    }

    @Override
    public Optional<User> findById(int userId) {
        return query(session -> Optional.ofNullable(session.get(User.class, userId)));
    }

    @Override
    public Optional<User> findByUserName(final String userName) {
        return query(session ->
            session.createNamedQuery(User.QUERY_FIND_BY_USERNAME, User.class)
                    .setParameter("userName", userName).uniqueResultOptional());
    }

    @Override
    public void delete(int userId) {
        execute(session -> {
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
        });
    }

    @Override
    public List<User> findAll() {
        return query(session -> session.createNamedQuery(User.QUERY_FIND_ALL, User.class).list());
    }
}
