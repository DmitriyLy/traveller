package org.dmly.traveller.app.rest.service.transport;

import io.swagger.annotations.*;
import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.rest.dto.transport.RouteDTO;
import org.dmly.traveller.app.rest.service.base.BaseResource;
import org.dmly.traveller.app.service.TransportService;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("routes")
@Api("routes")
public class RouteResource extends BaseResource {

    private final TransportService transportService;

    private final Transformer transformer;

    @Inject
    public RouteResource(TransportService transportService, Transformer transformer) {
        this.transportService = transportService;
        this.transformer = transformer;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all the existing routes")
    public List<RouteDTO> findAll() {
        return transportService.findRoutes().stream()
                .map(route -> transformer.transform(route, RouteDTO.class))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Saves route object", consumes = MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid content of the route object") })
    public Response save(@Valid @ApiParam(name = "route", required = true) RouteDTO routeDTO) {
        Route route = transformer.untransform(routeDTO, Route.class);
        transportService.saveRoute(route);

        return postForLocation(route.getId());
    }

    @Path("/{cityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns existing route by its identifier")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid city identifier"),
            @ApiResponse(code = 404, message = "Identifier of the non-existing city") })
    public Response findById(@ApiParam("Unique numeric city identifier") @PathParam("cityId") final int routeId) {
        Route route = transportService.findRouteById(routeId)
                .orElseThrow(() -> new InvalidParameterException("Invalid route identifier: " + routeId));

        return ok(transformer.transform(route, RouteDTO.class));
    }
}
