package org.dmly.traveller.app.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Setter;
import org.dmly.traveller.app.model.entity.geography.City;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "\"City with transport stations to book and purchase tickets")
@Setter
public class CityDTO extends BaseDTO<City> {

    @NotNull
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    private String district;

    @NotNull
    @Size(min = 2, max = 32)
    private String region;

    @ApiModelProperty(name = "Name of the city", required = true)
    public String getName() {
        return name;
    }

    @ApiModelProperty(name = "Name of the city's district. Empty for region center", required = false)
    public String getDistrict() {
        return district;
    }

    @ApiModelProperty(name = "Name of the city's region", required = true)
    public String getRegion() {
        return region;
    }
}
