package org.knowm.xchange.btcmarkets;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsOrderBook;
import org.knowm.xchange.btcmarkets.dto.marketdata.BTCMarketsTicker;
import org.knowm.xchange.btcmarkets.dto.v3.marketdata.BTCMarketsTrade;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BTCMarkets {

  @GET
  @Path("/market/{instrument}/{currency}/tick")
  BTCMarketsTicker getTicker(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("/market/{instrument}/{currency}/orderbook")
  BTCMarketsOrderBook getOrderBook(
      @PathParam("instrument") String instrument, @PathParam("currency") String currency)
      throws IOException;

  @GET
  @Path("/v3/markets/{marketId}/trades")
  List<BTCMarketsTrade> getTrades(@PathParam("marketId") String marketId) throws IOException;

  @GET
  @Path("/v3/markets/{marketId}/trades")
  List<BTCMarketsTrade> getTrades(
      @QueryParam("before") Long before,
      @QueryParam("after") Long after,
      @QueryParam("limit") Integer limit,
      @PathParam("marketId") String marketId)
      throws IOException;
}