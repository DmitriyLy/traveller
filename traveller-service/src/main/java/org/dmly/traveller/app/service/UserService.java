package org.dmly.traveller.app.service;

import org.dmly.traveller.app.model.entity.person.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void save(User user);

    Optional<User> findById(int userId);

    Optional<User> findByUserName(String userName);

    void delete(int userId);

    List<User> findAll();
}
