package org.dmly.traveller.geography.dto;

import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.app.infra.util.annotation.Ignore;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;
import org.dmly.traveller.common.dto.BaseDTO;
import org.dmly.traveller.geography.model.entity.Address;
import org.dmly.traveller.geography.model.entity.Coordinate;
import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.entity.TransportType;

@Getter
@Setter
public class StationDTO extends BaseDTO<Station> {

    @DomainProperty("city")
    private int cityId;

    private String zipCode;

    private String street;

    private String houseNo;

    private String apartment;

    private String phone;

    private double x;

    private double y;

    @Ignore
    private String transportType;

    //@Override
    /*public void transform(Station station) {
        //super.transform(station);
        zipCode = station.getAddress().getZipCode();
        street = station.getAddress().getStreet();
        apartment = station.getAddress().getApartment();
        houseNo = station.getAddress().getHouseNo();
        x = station.getCoordinate().getX();
        y = station.getCoordinate().getY();
        transportType = station.getTransportType().name();
    }*/

    //@Override
    /*public Station untransform(Station station) {
        if (station.getAddress() == null) {
            station.setAddress(new Address());
        }

        station.getAddress().setApartment(apartment);
        station.getAddress().setHouseNo(houseNo);
        station.getAddress().setStreet(street);
        station.getAddress().setZipCode(zipCode);

        if (station.getCoordinate() == null) {
            station.setCoordinate(new Coordinate());
        }

        station.getCoordinate().setX(x);
        station.getCoordinate().setY(y);
        station.setTransportType(TransportType.valueOf(transportType.toUpperCase()));

        //return super.untransform(station);
    }*/
}
