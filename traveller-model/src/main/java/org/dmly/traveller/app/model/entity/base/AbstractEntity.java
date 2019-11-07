package org.dmly.traveller.app.model.entity.base;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.dmly.traveller.app.model.entity.person.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Base class for all business entities
 *
 */

@MappedSuperclass
@Setter
@EqualsAndHashCode(of = "id")
public class AbstractEntity {
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TRANSPORT_TYPE = "transportType";

    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private User createdBy;
    private User modifiedBy;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(name = "MODIFIED_AT", insertable = false)
    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(name = "CREATED_BY", updatable = false)
    public User getCreatedBy() {
        return createdBy;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = {})
    @JoinColumn(name = "MODIFIED_BY", insertable = false)
    public User getModifiedBy() {
        return modifiedBy;
    }

    @PrePersist
    public void prePersist() {
        if (getId() == 0) {
            setCreatedAt(LocalDateTime.now());
        }
    }
}
