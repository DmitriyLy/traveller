package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.model.entity.person.User;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class HibernateUserRepository extends BaseHibernateRepository implements UserRepository {

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
