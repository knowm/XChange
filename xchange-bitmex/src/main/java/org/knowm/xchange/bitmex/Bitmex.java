package org.knowm.xchange.bitmex;

import java.io.IOException;
import java.util.Date;
import javax.annotation.Nullable;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitmex.dto.account.BitmexTickerList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexFundingList;
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

  @GET
  @Path("funding")
  BitmexFundingList getFundingHistory(
      @Nullable @QueryParam("symbol") String symbol,
      @Nullable @QueryParam("filter") String filter,
      @Nullable @QueryParam("columns") String columns,
      @Nullable @QueryParam("count") Integer count,
      @Nullable @QueryParam("start") Long start,
      @Nullable @QueryParam("reverse") Boolean reverse,
      @Nullable @QueryParam("startTime") Date startTime,
      @Nullable @QueryParam("endTime") Date endTime)
      throws IOException, BitmexException;
}
