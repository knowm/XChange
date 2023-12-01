package org.knowm.xchange.idex.service;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.knowm.xchange.idex.annotations.Api;
import org.knowm.xchange.idex.annotations.ApiOperation;
import org.knowm.xchange.idex.annotations.ApiResponse;
import org.knowm.xchange.idex.annotations.ApiResponses;
import org.knowm.xchange.idex.dto.TradeHistoryItem;
import org.knowm.xchange.idex.dto.TradeHistoryReq;

@Path("/returnTradeHistory")
@Api(description = "the returnTradeHistory API")
@Consumes("application/json")
@Produces("application/json")
public interface ReturnTradeHistoryApi {

  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @ApiOperation(value = "trade history", notes = "", tags = "market")
  @ApiResponses(
      @ApiResponse(
          code = 200,
          message = "",
          response = TradeHistoryItem.class,
          responseContainer = "List"))
  java.util.List<TradeHistoryItem> tradeHistory(TradeHistoryReq tradeHistoryReq);
}
