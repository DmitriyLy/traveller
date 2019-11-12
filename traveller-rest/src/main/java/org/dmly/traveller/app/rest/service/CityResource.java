package org.dmly.traveller.app.rest.service;

import io.swagger.annotations.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.rest.service.base.BaseResource;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.transform.Transformer;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
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
        City city = new City("Odessa");
        city.addStation(TransportType.AUTO);
        city.setDistrict("Odessa");
        city.setRegion("Odessa");
        service.saveCity(city);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns all the existing cities")
    public List<CityDTO> findCities() {
        return service.findCities().stream().map((city) -> transformer.transform(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCity(@Valid CityDTO cityDTO) {
        service.saveCity(transformer.untransform(cityDTO, City.class));
    }

    @Path("/{cityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns existing city by its identifier")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid city identifier"),
            @ApiResponse(code = 404, message = "Identifier of the non-existing city") })
    public Response findCityById(@ApiParam("Unique numeric city identifier") @PathParam("cityId") final String cityId) {
        if (!NumberUtils.isNumber(cityId)) {
            return BAD_REQUEST;
        }

        Optional<City> city = service.findCityById(NumberUtils.toInt(cityId));
        if (!city.isPresent()) {
            return NOT_FOUND;
        }
        return ok(transformer.transform(city.get(), CityDTO.class));
    }

    @PreDestroy
    public void preDestroy() {
    }

}
