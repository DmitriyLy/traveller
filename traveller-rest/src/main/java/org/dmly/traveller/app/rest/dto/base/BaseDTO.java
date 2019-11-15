package org.dmly.traveller.app.rest.dto.base;

import lombok.Getter;
import lombok.Setter;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.transform.Transformable;

@Getter
@Setter
public abstract class BaseDTO<T extends AbstractEntity> implements Transformable<T> {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Should be overridden in the derived classes if additional transformation
     * logic domain model -> DTO is needed.
     * Overridden methods should call super.transform()
     */
    public void transform(T t) {
        id = t.getId();
    }

    public T untransform(T t) {
        t.setId(getId());
        return t;
    }
}
