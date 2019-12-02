package org.dmly.traveller.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.common.model.entity.base.AbstractEntity;

@Getter
@Setter
public abstract class BaseDTO<T extends AbstractEntity> {
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
