package org.dmly.traveller.geography.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationDTO  {
    private int id;

    private int cityId;

    private String zipCode;

    private String street;

    private String houseNo;

    private String apartment;

    private String phone;

    private double x;

    private double y;

    private String transportType;
}
