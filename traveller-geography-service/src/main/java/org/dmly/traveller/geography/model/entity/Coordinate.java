package org.dmly.traveller.geography.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Setter @NoArgsConstructor @AllArgsConstructor
public class Coordinate {
    private double x;
    private double y;

    @Column(name = "X")
    public double getX() {
        return x;
    }

    @Column(name = "Y")
    public double getY() {
        return y;
    }
}
