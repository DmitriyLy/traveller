package org.dmly.traveller.presentation.admin.bean;

import org.dmly.traveller.app.infra.util.CommonUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@Named
@RequestScoped
public class CitiesBean {
    private final List<CityBean> cities;

    public CitiesBean() {
        cities = new ArrayList<>();
        cities.add(new CityBean("Odessa", "", "Odessa"));
        cities.add(new CityBean("Izmail", "", "Odessa"));
    }

    public List<CityBean> getCities() {
        return cities;
    }
}
