package org.dmly.traveller.app.persistence.repository;

import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;

import java.util.List;

public interface StationRepository {
    List<Station> findByCriteria(StationCriteria criteria);
}
