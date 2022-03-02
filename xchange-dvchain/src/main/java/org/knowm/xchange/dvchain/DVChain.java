package org.knowm.xchange.dvchain;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.dvchain.dto.DVChainException;
import org.knowm.xchange.dvchain.dto.marketdata.DVChainMarketResponse;
import org.knowm.xchange.dvchain.dto.trade.DVChainNewLimitOrder;
import org.knowm.xchange.dvchain.dto.trade.DVChainNewMarketOrder;
import org.knowm.xchange.dvchain.dto.trade.DVChainTrade;
import org.knowm.xchange.dvchain.dto.trade.DVChainTradesResponse;

@Path("api/v4")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DVChain {

  @GET
  @Path("trades")
  DVChainTradesResponse getTrades(
      @HeaderParam("Authorization") String apiKey,
      @HeaderParam("Pragma") String nocache,
      @HeaderParam("Cache-Control") String cache)
      throws DVChainException, IOException;

  @POST
  @Path("trade")
  DVChainTrade placeLimitOrder(
      DVChainNewLimitOrder newOrder, @HeaderParam("Authorization") String apiKey)
      throws DVChainException, IOException;

  @POST
  @Path("trade")
  DVChainTrade placeMarketOrder(
      DVChainNewMarketOrder newOrder, @HeaderParam("Authorization") String apiKey)
      throws DVChainException, IOException;

  @GET
  @Path("prices")
  DVChainMarketResponse getPrices(@HeaderParam("Authorization") String apiKey)
      throws DVChainException, IOException;

  @DELETE
  @Path("trades/{tradeId}")
  String cancelOrder(
      @PathParam("tradeId") String tradeId, @HeaderParam("Authorization") String apiKey)
      throws DVChainException, IOException;
}
