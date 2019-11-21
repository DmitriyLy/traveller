package org.dmly.traveller.app.rest.service;

import io.swagger.annotations.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.app.model.entity.geography.Station;
import org.dmly.traveller.app.rest.dto.StationDTO;
import org.dmly.traveller.app.rest.service.base.BaseResource;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("stations")
@Api("stations")
public class StationResource extends BaseResource {

    private final GeographicService service;

    private final Transformer transformer;

    @Inject
    public StationResource(GeographicService service, Transformer transformer) {
        this.service = service;
        this.transformer = transformer;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Saves station object", consumes = MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid content of station object") })
    public Response save(@Valid @ApiParam(name = "station", required = true) StationDTO stationDTO) {
        Station station = transformer.untransform(stationDTO, Station.class);
        service.saveStation(station);

        return postForLocation(station.getId());
    }

    @Path("/{stationId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns existing station by its identifier")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid station identifier"),
            @ApiResponse(code = 404, message = "Identifier of the non-existing station") })
    public Response findById(@ApiParam("Unique numeric station identifier") @PathParam("stationId") final int stationId) {
        Station station = service.findStationById(stationId)
                .orElseThrow(() -> new InvalidParameterException("Invalid route identifier: " + stationId));

        return ok(transformer.transform(station, StationDTO.class));
    }
}
