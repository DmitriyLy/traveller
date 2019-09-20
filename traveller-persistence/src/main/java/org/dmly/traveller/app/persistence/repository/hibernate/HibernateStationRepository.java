package org.dmly.traveller.app.persistence.repository.hibernate;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.dmly.traveller.app.infra.cdi.DBSource;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.search.criteria.StationCriteria;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.dmly.traveller.app.persistence.repository.StationRepository;

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
                criteria = criteria.createCriteria(Station.FIELD_NAME);
                criteria.add(Restrictions.eq(City.FIELD_NAME, stationCriteria.getName()));
            }

            return criteria.list();
        });
    }

}
