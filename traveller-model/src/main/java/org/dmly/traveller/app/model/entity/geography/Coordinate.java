package org.dmly.traveller.app.model.entity.geography;

import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Setter
public class Coordinate {
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Column(name = "X")
    public double getX() {
        return x;
    }

    @Column(name = "Y")
    public double getY() {
        return y;
    }
}
