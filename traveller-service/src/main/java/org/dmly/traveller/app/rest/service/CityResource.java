package org.dmly.traveller.app.rest.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.model.entity.transport.TransportType;
import org.dmly.traveller.app.rest.dto.CityDTO;
import org.dmly.traveller.app.rest.service.base.BaseResource;
import org.dmly.traveller.app.service.GeographicService;
import org.dmly.traveller.app.service.impl.GeographicServiceImpl;
import org.dmly.traveller.app.service.transform.Transformer;
import org.dmly.traveller.app.service.transform.impl.SimpleDTOTransformer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("cities")
@Api(value = "cities", description = "City-related operations")
public class CityResource extends BaseResource {

    private final GeographicService service;
    private final Transformer transformer;

    @Inject
    public CityResource(GeographicService service, Transformer transformer) {
        this.service = service;
        this.transformer = transformer;

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
        return service.findCities().stream()
                .map(city -> transformer.transform(city, CityDTO.class))
                .collect(Collectors.toList());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveCity(CityDTO cityDTO) {
        service.saveCity(transformer.untransform(cityDTO, City.class));
    }

    @Path("/{cityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCityById(@PathParam("cityId") final String cityId) {
        if (!NumberUtils.isNumber(cityId)) {
            return BAD_REQUEST;
        }

        Optional<City> city = service.findCityById(NumberUtils.toInt(cityId));

        if (!city.isPresent()) {
            return NOT_FOUND;
        }

        return ok(transformer.transform(city.get(), CityDTO.class));
    }
}
