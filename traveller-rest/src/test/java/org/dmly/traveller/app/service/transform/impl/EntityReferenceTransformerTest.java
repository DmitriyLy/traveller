package org.dmly.traveller.app.service.transform.impl;

import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.model.entity.loader.EntityLoader;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.rest.dto.transport.RouteDTO;
import org.dmly.traveller.app.service.transform.Transformer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EntityReferenceTransformerTest {

    private Transformer  entityReferenceTransformer;

    @Mock
    private EntityLoader entityLoader;

    @Before
    public void setup() {
        entityReferenceTransformer = new EntityReferenceTransformer(entityLoader,
                new CachedFieldProvider());
    }

    @Test
    public void transform_validEntity_referenceFieldsCopied() {
        Station start = new Station();
        start.setId(1);
        Station dest = new Station();
        dest.setId(2);

        Route route = new Route();
        route.setStart(start);
        route.setDestination(dest);

        RouteDTO routeDTO = entityReferenceTransformer.transform(route, RouteDTO.class);
        assertNotNull(routeDTO);
        assertEquals(start.getId(), routeDTO.getStartId());
        assertEquals(dest.getId(), routeDTO.getDestinationId());
    }

    @Test
    public void untransform_validDTO_referenceFieldsCopied() {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setStartId(1);
        routeDTO.setDestinationId(2);

        Station start = new Station();
        start.setId(1);
        Station dest = new Station();
        dest.setId(2);
        when(entityLoader.load(Station.class, 1)).thenReturn(start);
        when(entityLoader.load(Station.class, 2)).thenReturn(dest);

        Route route = entityReferenceTransformer.untransform(routeDTO, Route.class);
        assertNotNull(route);
        assertEquals(start, route.getStart());
        assertEquals(dest, route.getDestination());
    }

}