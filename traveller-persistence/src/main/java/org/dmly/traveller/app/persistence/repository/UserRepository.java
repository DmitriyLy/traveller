package org.dmly.traveller.app.persistence.repository;

import org.dmly.traveller.app.model.entity.person.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(int userId);

    void delete(int userId);

    List<User> findAll();
}
