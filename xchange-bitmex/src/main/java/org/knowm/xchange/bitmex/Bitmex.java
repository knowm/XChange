package org.knowm.xchange.bitmex;

import java.io.IOException;
import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexKlineList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrderList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTradeList;
import org.knowm.xchange.bitmex.dto.marketdata.results.BitmexSymbolsAndPromptsResult;

@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitmex {

  @GET
  @Path("trade")
  BitmexPublicTradeList getTrades(
      @QueryParam("symbol") String currencyPair,
      @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start)
      throws IOException, BitmexException;

  @GET
  @Path("trade/bucketed")
  BitmexKlineList getBucketedTrades(
      @QueryParam("binSize") String binSize,
      @QueryParam("partial") Boolean partial,
      @QueryParam("symbol") String symbol,
      @QueryParam("count") Long count,
      @QueryParam("reverse") Boolean reverse)
      throws IOException, BitmexException;

  @GET
  @Path("orderBook/L2")
  BitmexPublicOrderList getDepth(
      @QueryParam("symbol") String currencyPair, @QueryParam("depth") Double depth)
      throws IOException, BitmexException;

  @GET
  @Path("instrument")
  BitmexTickerList getTickers(
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse)
      throws IOException, BitmexException;

  @GET
  @Path("instrument")
  BitmexTickerList getTicker(@QueryParam("symbol") String symbol)
      throws IOException, BitmexException;

  @GET
  @Path("instrument/active")
  BitmexTickerList getActiveTickers() throws IOException, BitmexException;

  @GET
  @Path("instrument/activeIntervals")
  BitmexSymbolsAndPromptsResult getPromptsAndSymbols() throws IOException, BitmexException;
}
