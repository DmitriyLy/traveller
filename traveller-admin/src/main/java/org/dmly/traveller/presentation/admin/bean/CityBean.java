package org.dmly.traveller.presentation.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.transform.Transformable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name="currentCity")
@ViewScoped
@ToString
@Getter @Setter
public class CityBean implements Transformable<City> {
    private int id;
    private String name;
    private String district;
    private String region;

    public void clear() {
        id = 0;
        setName("");
        setDistrict("");
        setRegion("");
    }


    @Override
    public void transform(City city) {

    }

    @Override
    public City untransform(City city) {
        return city;
    }
}
