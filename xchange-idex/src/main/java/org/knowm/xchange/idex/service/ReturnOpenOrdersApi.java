package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.OpenOrdersReq;
import org.knowm.xchange.idex.dto.ReturnOpenOrdersResponse;

@Path("/returnOpenOrders")
@Api(description = "the returnOpenOrders API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnOpenOrdersApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value = "Returns the open orders for a given market and address",
      notes = "",
      tags = "trade")
  @ApiResponses(@ApiResponse(code = 200, message = "", response = ReturnOpenOrdersResponse.class))
  ReturnOpenOrdersResponse openOrders(OpenOrdersReq openOrdersReq);
}
