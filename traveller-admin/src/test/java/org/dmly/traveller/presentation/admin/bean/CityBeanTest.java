package org.dmly.traveller.presentation.admin.bean;

import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.model.entity.geography.City;
import org.junit.Test;

import static org.junit.Assert.*;

public class CityBeanTest {

    @Test
    public void clear_beanInitialized_allFieldsCleared() {
        CityBean cityBean = new CityBean();
        cityBean.setId(1);
        cityBean.setName("test");
        cityBean.setDistrict("test");
        cityBean.setRegion("test");
        cityBean.clear();
        assertTrue(StringUtils.isEmpty(cityBean.getName()));
        assertTrue(StringUtils.isEmpty(cityBean.getDistrict()));
        assertTrue(StringUtils.isEmpty(cityBean.getRegion()));
        assertEquals(0, cityBean.getId());
    }

    @Test
    public void untrasform_cityInitialized_cityReturned() {
        City city = new City();
        CityBean cityBean = new CityBean();
        City newCity = cityBean.untransform(city);
        assertSame(city, newCity);
    }

}