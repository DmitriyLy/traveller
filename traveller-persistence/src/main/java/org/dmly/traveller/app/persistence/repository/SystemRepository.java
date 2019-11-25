package org.dmly.traveller.app.persistence.repository;

import org.dmly.traveller.app.infra.exception.PersistenceException;

public interface SystemRepository {

    void healthCheck() throws PersistenceException;

}
