package org.dmly.traveller.user.model.entity;

import lombok.Setter;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;

import javax.persistence.*;


/**
 * Entity that encapsulates user of the application
 *
 */
@Table(name = "USER")
@Entity
@NamedQueries({
        @NamedQuery(name = User.QUERY_FIND_ALL, query = "from User"),
        @NamedQuery(name = User.QUERY_FIND_BY_USERNAME, query = "from User where userName = :userName")
})
@Setter
public class User extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "User.findAll";
    public static final String QUERY_FIND_BY_USERNAME = "User.findByUserName";

    private String userName;
    private String password;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 24)
    public String getUserName() {
        return userName;
    }

    @Column(name = "PASSWORD", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }
}
