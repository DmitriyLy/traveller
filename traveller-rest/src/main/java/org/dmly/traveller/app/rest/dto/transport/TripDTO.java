package org.dmly.traveller.app.rest.dto.transport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.app.model.entity.travel.Trip;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(description = "Trip with exact date, time and price")
public class TripDTO extends BaseDTO<Trip> {

    @ApiModelProperty(name = "Identifier of the parent route", required = true)
    @Positive
    private int routeId;

    @ApiModelProperty(name = "Trip departure time", required = true)
    @NotNull
    private LocalDateTime startTime;

    @ApiModelProperty(name = "Trip arrival time", required = true)
    @NotNull
    private LocalDateTime endTime;

    @ApiModelProperty(name = "Maximum number of seats in the trip", required = true)
    @Positive
    private int maxSeats;

    @ApiModelProperty(name = "Number of available seats in the trip", required = true)
    @PositiveOrZero
    private int availableSeats;

    @ApiModelProperty(name = "Ticket price", required = true)
    @Positive
    private double price;

}
