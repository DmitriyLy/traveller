package org.dmly.traveller.app.monitoring.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dmly.traveller.app.persistence.hibernate.SessionFactoryBuilder;
import org.hibernate.Session;

import javax.persistence.Query;

@RequiredArgsConstructor
@Slf4j
public class MySQLHealthCheck extends HealthCheck {

    private final SessionFactoryBuilder sessionFactoryBuilder;

    @Override
    protected Result check() throws Exception {

        try (Session session = sessionFactoryBuilder.getSessionFactory().openSession()) {
            Query query = session.createNativeQuery("SHOW TABLES");
            query.getResultList();

            log.info("MYSQL healthcheck is successful");

            return HealthCheck.Result.healthy();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return HealthCheck.Result.unhealthy(e);
        }
    }
}
