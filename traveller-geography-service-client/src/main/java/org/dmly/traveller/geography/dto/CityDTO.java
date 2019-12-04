package org.dmly.traveller.geography.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@ApiModel(description = "\"City with transport stations to book and purchase tickets")
@Setter @Getter
public class CityDTO {
    @PositiveOrZero
    @ApiModelProperty(name = "City unique identifier", required = true)
    private int id;

    @NotNull
    @Size(min = 2, max = 32)
    @ApiModelProperty(name = "Name of the city", required = true)
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    @ApiModelProperty(name = "Name of the city's district. Empty for region center", required = false)
    private String district;

    @NotNull
    @Size(min = 2, max = 32)
    @ApiModelProperty(name = "Name of the city's region", required = true)
    private String region;
}
