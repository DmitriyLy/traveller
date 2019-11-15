package org.dmly.traveller.app.persistence.hibernate.loader;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.hibernate.HibernateCityRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionEntityLoaderTest {
    private EntityLoader entityLoader;

    private CityRepository cityRepository;

    @Before
    public void setup() {
        SessionFactoryBuilder builder = new SessionFactoryBuilder();
        entityLoader = new SessionEntityLoader(builder);
        cityRepository = new HibernateCityRepository(builder);

        City city = new City();
        city.setName("Odessa");
        city.setDistrict("N/A");
        city.setRegion("Odessa");

        cityRepository.save(city);
    }

    @Test
    public void load_nonExistingCityIdentifier_returnsNull() {
        City city = entityLoader.load(City.class, 1000);
        assertNull(city);
    }

    @Test
    public void load_existingCityIdentifier_returnsValidCity() {
        City city = entityLoader.load(City.class, 1);
        assertNotNull(city);
        assertEquals("Odessa", city.getName());
    }

}