package org.dmly.traveller.geography.persistence.repository.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.hibernate.BaseHibernateRepository;
import org.dmly.traveller.common.infra.cdi.DBSource;
import org.dmly.traveller.geography.model.entity.City;
import org.dmly.traveller.geography.model.entity.Station;
import org.dmly.traveller.geography.model.search.criteria.StationCriteria;
import org.dmly.traveller.geography.persistence.repository.StationRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@DBSource
public class HibernateStationRepository extends BaseHibernateRepository implements StationRepository {

    @Inject
    public HibernateStationRepository(SessionFactoryBuilder builder) {
        super(builder);
    }

    @Override
    public List<Station> findByCriteria(StationCriteria stationCriteria) {
        return query(session -> {
            Criteria criteria = session.createCriteria(Station.class);

            if (stationCriteria.getTransportType() != null) {
                criteria.add(Restrictions.eq(Station.FIELD_TRANSPORT_TYPE, stationCriteria.getTransportType()));
            }

            if (!StringUtils.isEmpty(stationCriteria.getName())) {
                criteria = criteria.createCriteria(Station.FIELD_CITY);
                criteria.add(Restrictions.eq(City.FIELD_NAME, stationCriteria.getName()));
            }

            return criteria.list();
        });
    }

    @Override
    public Station findById(int stationId) {
        return query(session -> session.get(Station.class, stationId));
    }

    @Override
    public void save(Station station) {
        execute(session -> session.saveOrUpdate(station));
    }
}
