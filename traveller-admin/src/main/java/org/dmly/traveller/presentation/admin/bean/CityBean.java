package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.model.entity.geography.City;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="currentCity")
@ViewScoped
public class CityBean extends City {
    public void clear() {
        setName("");
        setDistrict("");
        setRegion("");
    }

    public void update(City city) {
        setName(city.getName());
        setDistrict(city.getDistrict());
        setRegion(city.getRegion());
        setId(city.getId());
    }
}
