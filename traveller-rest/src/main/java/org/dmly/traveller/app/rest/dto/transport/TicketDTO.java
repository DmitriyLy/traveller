package org.dmly.traveller.app.rest.dto.transport;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.dmly.traveller.app.model.entity.travel.Ticket;
import org.dmly.traveller.app.rest.dto.base.BaseDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel(description = "Trip ticket for the single passenger")
public class TicketDTO extends BaseDTO<Ticket> {

    @Positive
    @ApiModelProperty(name = "Identifier of the trip", required = true)
    private int tripId;

    @NotNull
    @Size(min = 2, max = 32)
    @ApiModelProperty(name = "Customer name", required = true)
    private String name;

    @NotNull
    @Size(min = 32, max = 32)
    @ApiModelProperty(name = "Auto-generated ticket identifier", required = true)
    private String uid;

}
