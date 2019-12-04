package org.dmly.traveller.geography.dto.transformable;

import org.dmly.traveller.common.infra.util.ReflectionUtil;
import org.dmly.traveller.common.model.transform.Transformable;
import org.dmly.traveller.geography.dto.StationDTO;
import org.dmly.traveller.geography.model.entity.Address;
import org.dmly.traveller.geography.model.entity.Coordinate;
import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.entity.TransportType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class StationTransformable implements Transformable<Station, StationDTO> {

    private final Map<String, String> domainMappings;

    public StationTransformable() {
        domainMappings = Map.of("cityId", "city");
    }

    @Override
    public StationDTO transform(final Station station, final StationDTO dto) {
        dto.setZipCode(station.getAddress().getZipCode());
        dto.setStreet(station.getAddress().getStreet());
        dto.setApartment(station.getAddress().getApartment());
        dto.setHouseNo(station.getAddress().getHouseNo());
        dto.setX(station.getCoordinate().getX());
        dto.setY(station.getCoordinate().getY());
        dto.setTransportType(station.getTransportType().name());

        return dto;
    }

    @Override
    public Station untransform(final StationDTO dto, final Station station) {
        if(station.getAddress() == null) {
            station.setAddress(new Address());
        }
        station.getAddress().setApartment(dto.getApartment());
        station.getAddress().setHouseNo(dto.getHouseNo());
        station.getAddress().setStreet(dto.getStreet());
        station.getAddress().setZipCode(dto.getZipCode());

        if(station.getCoordinate() == null) {
            station.setCoordinate(new Coordinate());
        }

        station.getCoordinate().setX(dto.getX());
        station.getCoordinate().setY(dto.getY());
        station.setTransportType(TransportType.valueOf(dto.getTransportType().toUpperCase()));

        return station;
    }

    @Override
    public List<Field> getIgnoredFields() {
        return List.of(ReflectionUtil.getField(StationDTO.class, "transportType"));
    }

    @Override
    public Map<String, String> getSourceMapping() {
        return domainMappings;
    }
}
