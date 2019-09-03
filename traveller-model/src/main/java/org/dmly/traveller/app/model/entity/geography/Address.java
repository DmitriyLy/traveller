package org.dmly.traveller.app.model.entity.geography;

import org.dmly.traveller.app.model.entity.base.AbstractEntity;

/**
 * Value type that stores address attributes
 * of the specific office or person
 *
 */
public class Address extends AbstractEntity {
    private String zipCode;
    private String street;
    private String houseNo;
    private String apartment;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
