package org.knowm.xchange.poloniex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexDepth;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;

@Path("public")
@Produces(MediaType.APPLICATION_JSON)
public interface Poloniex {

  @GET
  HashMap<String, PoloniexCurrencyInfo> getCurrencyInfo(@QueryParam("command") String command) throws PoloniexException, IOException;

  @GET
  HashMap<String, PoloniexMarketData> getTicker(@QueryParam("command") String command) throws PoloniexException, IOException;

  @GET
  PoloniexDepth getOrderBook(@QueryParam("command") String command,
      @QueryParam("currencyPair") String currencyPair) throws PoloniexException, IOException;

  @GET
  PoloniexDepth getOrderBook(@QueryParam("command") String command, @QueryParam("currencyPair") String currencyPair,
      @QueryParam("depth") Integer depth) throws PoloniexException, IOException;

  @GET
  PoloniexPublicTrade[] getTrades(@QueryParam("command") String command, @QueryParam("currencyPair") String currencyPair,
      @QueryParam("start") Long startTime, @QueryParam("end") Long endTime) throws PoloniexException, IOException;

  @GET
  Map<String, PoloniexDepth> getAllOrderBooks(@QueryParam("command") String command, @QueryParam("currencyPair") String pair,
      @QueryParam("depth") Integer depth) throws PoloniexException, IOException;

  @GET
  PoloniexChartData[] getChartData(@QueryParam("command") String command, @QueryParam("currencyPair") String pair,
      @QueryParam("start") Long startTime, @QueryParam("end") Long endTime, @QueryParam("period") long period) throws PoloniexException, IOException;

}
