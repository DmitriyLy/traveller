package org.dmly.traveller.app.rest.dto;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

public class CityDTO extends BaseDTO<City> {
    private String name;
    private String district;
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
