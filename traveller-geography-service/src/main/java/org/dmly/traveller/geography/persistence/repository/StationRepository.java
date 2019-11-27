package org.dmly.traveller.geography.persistence.repository;

import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.search.criteria.StationCriteria;

import java.util.List;

public interface StationRepository {
    List<Station> findByCriteria(StationCriteria criteria);

    Station findById(int stationId);

    void save(Station station);
}
