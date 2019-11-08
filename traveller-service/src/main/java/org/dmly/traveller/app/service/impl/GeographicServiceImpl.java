package org.dmly.traveller.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.infra.exception.flow.ValidationException;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.StationRepository;
import org.dmly.traveller.app.service.GeographicService;

/**
 * Default implementation of the {@link GeographicService}
 *
 */
@Named
public class GeographicServiceImpl implements GeographicService {
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final Validator validator;

    @Inject
    public GeographicServiceImpl(@DBSource CityRepository cityRepository, @DBSource StationRepository stationRepository) {
        this.cityRepository = cityRepository;
        this.stationRepository = stationRepository;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public List<City> findCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findCityById(int id) {
        return Optional.ofNullable(cityRepository.findById(id));
    }

    @Override
    public List<Station> searchStations(StationCriteria criteria, RangeCriteria rangeCriteria) {
       return stationRepository.findByCriteria(criteria);
    }

    @Override
    public void saveCity(City city) {
        Set constraintViolations = validator.validate(city);
        if (!constraintViolations.isEmpty()) {
            throw new ValidationException("City validation failure", constraintViolations);
        }
        cityRepository.save(city);
    }

    @Override
    public void saveCities(List<City> cities) {
        cityRepository.saveAll(cities);
    }

    @Override
    public void deleteCities() {
        cityRepository.deleteAll();
    }

    @Override
    public void deleteCity(int id) {
        cityRepository.delete(id);
    }
}
