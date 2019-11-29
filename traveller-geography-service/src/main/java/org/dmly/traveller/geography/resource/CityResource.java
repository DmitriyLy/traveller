package org.dmly.traveller.geography.resource;

import io.swagger.annotations.*;
import org.dmly.traveller.app.infra.exception.flow.InvalidParameterException;
import org.dmly.traveller.common.model.transform.Transformer;
import org.dmly.traveller.geography.dto.CityDTO;
import org.dmly.traveller.geography.model.entity.City;
import org.dmly.traveller.geography.resource.base.BaseResource;
import org.dmly.traveller.geography.service.GeographicService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("cities")
@Api("cities")
public class CityResource extends BaseResource {

    private final GeographicService service;

    private final Transformer transformer;

    @Inject
    public CityResource(GeographicService service, Transformer transformer) {
        this.transformer = transformer;

        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all the existing cities")
    public List<CityDTO> findCities() {
        return service.findCities().stream().map(city -> transformer.transform(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Saves city object", consumes = MediaType.APPLICATION_JSON)
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid content of city object") })
    public Response save(@Valid @ApiParam(name = "city", required = true) CityDTO cityDTO) {
        City city = transformer.untransform(cityDTO, City.class);
        service.saveCity(city);

        return postForLocation(city.getId());
    }

    @Path("/{cityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns existing city by its identifier")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid city identifier"),
            @ApiResponse(code = 404, message = "Identifier of the non-existing city") })
    public Response findById(@ApiParam("Unique numeric city identifier") @PathParam("cityId") final int cityId) {
       City city = service.findCityById(cityId)
               .orElseThrow(() -> new InvalidParameterException("Invalid city identifier: " + cityId));

        return ok(transformer.transform(city, CityDTO.class));
    }
}
