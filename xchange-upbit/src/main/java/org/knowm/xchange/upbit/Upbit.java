package org.knowm.xchange.upbit;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import java.io.IOException;
import java.util.List;
import org.knowm.xchange.upbit.dto.UpbitException;
import org.knowm.xchange.upbit.dto.marketdata.*;

@Path("v1")
public interface Upbit {

  @GET
  @Path("ticker")
  UpbitTickers getTicker(@QueryParam("markets") String markets) throws IOException, UpbitException;

  @GET
  @Path("market/all")
  List<UpbitMarket> getMarketAll() throws IOException, UpbitException;

  @GET
  @Path("orderbook")
  UpbitOrderBooks getOrderBook(@QueryParam("markets") String markets)
      throws IOException, UpbitException;

  @GET
  @Path("trades/ticks")
  UpbitTrades getTrades(@QueryParam("market") String market, @QueryParam("count") int count)
      throws IOException, UpbitException;

  @GET
  @Path("candles/{timeUnit}/{timeUnitCount}")
  List<UpbitCandleStickData> getCandleStick(
      @PathParam("timeUnit") String timeUnit,
      @PathParam("timeUnitCount") long timeUnitCount,
      @QueryParam("market") String market,
      @QueryParam("to") String to,
      @QueryParam("count") Integer count)
      throws IOException, UpbitException;
}
