package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.OrderBookReq;
import org.knowm.xchange.idex.dto.ReturnOrderBookResponse;

@Path("/returnOrderBook")
@Api(description = "the returnOrderBook API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnOrderBookApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(
      value =
          "Returns the orderbook for a given market, or returns an object of the entire orderbook keyed by\\ market if the market parameter is omitted.",
      notes = "",
      tags = "market")
  @ApiResponses(@ApiResponse(code = 200, message = "", response = ReturnOrderBookResponse.class))
  ReturnOrderBookResponse orderBook(OrderBookReq orderBookReq);
}
