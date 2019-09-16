package org.dmly.traveller.app.service.impl;

import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.model.search.criteria.range.RangeCriteria;
import org.dmly.traveller.app.persistence.repository.CityRepository;
import org.dmly.traveller.app.persistence.repository.StationRepository;
import org.dmly.traveller.app.service.GeographicService;

import javax.inject.Inject;
import javax.validation.*;
import java.util.*;
import org.dmly.traveller.app.infra.exception.flow.ValidationException;

/**
 * Default implementation of the {@link GeographicService}
 *
 */
public class GeographicServiceImpl implements GeographicService {
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final Validator validator;

    @Inject
    public GeographicServiceImpl(CityRepository cityRepository, StationRepository stationRepository) {
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
        Set<ConstraintViolation<City>> constraintViolations = validator.validate(city);
        if (!constraintViolations.isEmpty()) {
            throw new ValidationException("City validation failure", constraintViolations);
        }
        cityRepository.save(city);
    }

    @Override
    public void deleteCities() {
        cityRepository.deleteAll();
    }
}
