package org.dmly.traveller.user.service.impl;

import org.dmly.traveller.app.infra.util.SecurityUtil;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.user.model.entity.User;
import org.dmly.traveller.user.persistence.repository.UserRepository;
import org.dmly.traveller.user.service.UserService;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(@DBSource UserRepository userRepository) {
        this.userRepository = userRepository;

        if (!userRepository.findByUserName("guest").isPresent()) {
            User user = new User();
            user.setUserName("guest");
            user.setPassword(SecurityUtil.encryptSHA("guest"));
            userRepository.save(user);
        }
    }


    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public void delete(int userId) {

    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
