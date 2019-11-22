package org.dmly.traveller.app.service.loader;

import org.dmly.traveller.app.infra.exception.ConfigurationException;
import org.dmly.traveller.app.model.entity.base.AbstractEntity;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.TransportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ServiceEntityLoaderTest {

    @Mock
    private GeographicService geographicService;

    @Mock
    private TransportService transportService;

    @Spy
    @InjectMocks
    private ServiceEntityLoader serviceEntityLoader;

    @Test
    public void load_validCityIdentifier_returnsCity() {
        int cityId = 1;
        City city = new City();
        city.setId(cityId);

        when(geographicService.findCityById(cityId)).thenReturn(Optional.of(city));
        City dbCity = serviceEntityLoader.load(City.class, cityId);
        assertNotNull(dbCity);
        assertEquals(cityId, dbCity.getId());

        verify(geographicService, times(1)).findCityById(cityId);
    }

    @Test
    public void load_nonExistingCityIdentifier_returnsNull() {
        when(geographicService.findCityById(anyInt())).thenReturn(Optional.empty());
        City dbCity = serviceEntityLoader.load(City.class, 1);
        assertNull(dbCity);

        verify(geographicService, times(1)).findCityById(anyInt());
    }

    @Test(expected = ConfigurationException.class)
    public void load_notSupportedEntityClass_throwsException() {
        serviceEntityLoader.load(SomeEntity.class, 1);
    }

    private static class SomeEntity extends AbstractEntity {
    }

}