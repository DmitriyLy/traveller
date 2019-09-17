package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.service.transform.Transformer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleDTOTransformerTest {
    private Transformer transformer;

    @Before
    public void setUp() throws Exception {
        transformer = new SimpleDTOTransformer();
    }

    @Test
    public void testTransformCitySuccess() {
        City city = new City("Odessa");
        city.setId(1);
        city.setRegion("OD");
        city.setDistrict("N/A");

        CityDTO cityDTO = transformer.transform(city, CityDTO.class);

        assertNotNull(cityDTO);
        assertEquals(city.getId(), cityDTO.getId());
        assertEquals(city.getName(), cityDTO.getName());
        assertEquals(city.getDistrict(), cityDTO.getDistrict());
        assertEquals(city.getRegion(), cityDTO.getRegion());
    }

    @Test(expected = InvalidParameterException.class)
    public void testTransformNullCityFailure() {
        transformer.transform(null, CityDTO.class);
    }

    @Test(expected = InvalidParameterException.class)
    public void testTransformNullDTOClassFailure() {
        transformer.transform(new City("Odessa"), null);
    }

    @Test
    public void testUntransformCitySuccess() {
        CityDTO dto = new CityDTO();
        dto.setId(1);
        dto.setRegion("OD");
        dto.setDistrict("N/A");
        dto.setName("Odessa");

        City city = transformer.untransform(dto, City.class);

        assertNotNull(city);
        assertEquals(dto.getId(), city.getId());
        assertEquals(dto.getRegion(), city.getRegion());
        assertEquals(dto.getDistrict(), city.getDistrict());
        assertEquals(dto.getName(), city.getName());


    }

    @Test(expected = InvalidParameterException.class)
    public void testUntrasformNullDtoFailure() {
        transformer.untransform(null, City.class);
    }

    @Test(expected = InvalidParameterException.class)
    public void testUntransformNullEntityClassFailure() {
        transformer.untransform(new CityDTO(), null);
    }
}