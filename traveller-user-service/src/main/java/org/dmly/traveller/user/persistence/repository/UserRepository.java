package org.dmly.traveller.user.persistence.repository;

import org.dmly.traveller.user.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findById(int userId);

    Optional<User> findByUserName(String userName);

    void delete(int userId);

    List<User> findAll();
}
