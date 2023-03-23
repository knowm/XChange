package org.knowm.xchange.dvchain;

import java.io.IOException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
