package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.OrderTradesReq;
import org.knowm.xchange.idex.dto.ReturnOrderTradesResponse;

@Path("/returnOrderTrades")
@Api(description = "the returnOrderTrades API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnOrderTradesApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Returns all trades involving a given order hash, specified by the orderHash property of the JSON input.",
      notes = "",
      tags = "trade")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message =
              "Sample output: [ { date: '2017-10-11 21:41:15',amount: '0.3',type: 'buy',total: '1',price: '0.3',uuid: 'e8719a10-aecc-11e7-9535-3b8451fd4699',transactionHash: '0x28b945b586a5929c69337929533e04794d488c2d6e1122b7b915705d0dff8bb6' } ]",
          response = ReturnOrderTradesResponse.class))
  ReturnOrderTradesResponse orderTrades(OrderTradesReq orderTradesReq);
}
