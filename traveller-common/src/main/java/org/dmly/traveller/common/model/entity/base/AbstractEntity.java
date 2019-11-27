package org.dmly.traveller.common.model.entity.base;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Base class for all business entities
 *
 */

@MappedSuperclass
@Setter
@EqualsAndHashCode(of = "id")
public abstract class AbstractEntity {
    public static final String FIELD_CREATED_AT = "createdAt";
    public static final String FIELD_CITY = "city";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_TRANSPORT_TYPE = "transportType";
    public static final String FIELD_ID = "id";

    private int id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String createdBy;
    private String modifiedBy;

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

    @Column(name = "CREATED_BY", updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    @Column(name = "MODIFIED_BY", updatable = false)
    public String getModifiedBy() {
        return modifiedBy;
    }

    @PrePersist
    public void prePersist() {
        if (getId() == 0) {
            setCreatedAt(LocalDateTime.now());
        }
    }
}
