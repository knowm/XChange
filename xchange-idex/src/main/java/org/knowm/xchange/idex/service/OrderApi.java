package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.OrderReq;
import org.knowm.xchange.idex.dto.OrderResponse;

@Path("/order")
@Api(description = "the order API")
@Consumes("application/json")
@Produces("application/json")
public interface OrderApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(value = "Limit Order", notes = "", tags = "trade")
  @ApiResponses(@ApiResponse(code = 200, message = "", response = OrderResponse.class))
  OrderResponse order(OrderReq orderReq);
}
