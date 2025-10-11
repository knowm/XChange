package org.knowm.xchange.dase;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.dase.dto.marketdata.DaseCandlesResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketConfig;
import org.knowm.xchange.dase.dto.marketdata.DaseMarketsResponse;
import org.knowm.xchange.dase.dto.marketdata.DaseOrderBookSnapshot;
import org.knowm.xchange.dase.dto.marketdata.DaseTicker;
import org.knowm.xchange.dase.dto.marketdata.DaseTrade;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface DaseV1 {

  @GET
  @Path("/markets")
  DaseMarketsResponse getMarkets() throws IOException;

  @GET
  @Path("/markets/{market}")
  DaseMarketConfig getMarket(@PathParam("market") String market) throws IOException;

  @GET
  @Path("/markets/{market}/ticker")
  DaseTicker getTicker(@PathParam("market") String market) throws IOException;

  @GET
  @Path("/markets/{market}/snapshot")
  DaseOrderBookSnapshot getSnapshot(@PathParam("market") String market) throws IOException;

  @GET
  @Path("/markets/{market}/trades")
  DaseTradesResponse getTrades(
      @PathParam("market") String market,
      @QueryParam("limit") Integer limit,
      @QueryParam("before") String before)
      throws IOException;

  @GET
  @Path("/markets/{market}/candles")
  DaseCandlesResponse getCandles(
      @PathParam("market") String market,
      @QueryParam("granularity") String granularity,
      @QueryParam("from") Long from,
      @QueryParam("to") Long to)
      throws IOException;

  class DaseTradesResponse {
    public List<DaseTrade> trades;
  }
}
