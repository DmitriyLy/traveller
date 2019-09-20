package org.dmly.traveller.app.model.entity.person;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
;import javax.persistence.*;

/**
 * Entity that encapsulates user of the application
 *
 */
@Table(name = "USER")
@Entity
@NamedQueries({
        @NamedQuery(name = User.QUERY_FIND_ALL, query = "from User")
})
public class User extends AbstractEntity {
    public static final String QUERY_FIND_ALL = "user.findAll";

    private String userName;
    private String password;

    @Column(name = "USERNAME", nullable = false, unique = true, length = 24)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "PASSWORD", nullable = false, length = 24)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
