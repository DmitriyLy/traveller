package org.dmly.traveller.app.rest.dto.transport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.app.model.entity.travel.Route;
import org.dmly.traveller.app.model.transform.annotation.DomainProperty;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalTime;

@Setter
@Getter
@ApiModel(description = "Generic route that exists between start and destination stations")
public class RouteDTO extends BaseDTO<Route> {

    @ApiModelProperty(name = "Identifier of the start station", required = true)
    @Positive
    @DomainProperty("start")
    private int startId;

    @ApiModelProperty(name = "Identifier of the destination station", required = true)
    @Positive
    @DomainProperty("destination")
    private int destinationId;

    @ApiModelProperty(name = "Route departure time", required = true)
    @NotNull
    private LocalTime startTime;

    @ApiModelProperty(name = "Route arrival time", required = true)
    @NotNull
    private LocalTime endTime;

    @ApiModelProperty(name = "Generic ticket price", required = true)
    @Positive
    private double price;
}
