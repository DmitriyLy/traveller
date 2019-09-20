package org.dmly.traveller.app.persistence.repository.hibernate;

import org.dmly.traveller.app.model.entity.person.User;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HibernateUserRepositoryTest {
    private UserRepository repository;

    @Before
    public void setUp() throws Exception {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        repository = new HibernateUserRepository(builder);
    }

    @Test
    public void findAll_InitialInvocation_ListEmpty() {
        User user = new User();
        user.setUserName("user");
        user.setPassword("password");

        repository.save(user);

        List<User> users = repository.findAll();

        assertFalse(users.isEmpty());
    }

    @Test
    public void save_ValidUserObject_UserSaved() {
        int userCount = repository.findAll().size();

        User user = new User();
        user.setUserName("username" + (int)(Math.random() * 1000));
        user.setPassword("password");
        repository.save(user);

        List<User> users = repository.findAll();

        assertEquals(userCount + 1, users.size());
    }

    @Test(expected = Exception.class)
    public void save_UserExists_ExceptionThrown() {
        repository.save(null);

        assertTrue(false);
    }

    @Test
    public void delete_UserExists_UserDeleted() {
        int userNumber = repository.findAll().size();

        User user = new User();
        user.setUserName("username" + (int)(Math.random() * 1000));
        user.setPassword("password");

        repository.save(user);

        assertEquals(userNumber + 1, repository.findAll().size());

        repository.delete(user.getId());

        assertEquals(userNumber, repository.findAll().size());
    }
}