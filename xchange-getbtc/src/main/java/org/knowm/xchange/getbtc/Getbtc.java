package org.knowm.xchange.getbtc;

import java.io.IOException;
import java.util.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcOrderbook;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTicker;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTickerResponse;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTransaction;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface Getbtc {

  @GET
  @Path("stats")
  /**
   * Test connectivity to the Rest API. BTC/USD
   *
   * @return
   * @throws IOException
   */
  String getStats() throws IOException;

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("order-book")
  GetbtcOrderbook getOrderBook(@QueryParam("currency") String currency) throws IOException;

  /** Returns ticker by currency */
  @GET
  @Path("ticker")
  GetbtcTickerResponse getTicker(@QueryParam("currency") String currency) throws IOException;

  /** Returns tickers */
  @GET
  @Path("tickers")
  Map<String, GetbtcTicker> getTickers() throws IOException;

  /** Returns Transactions by currency */
  @GET
  @Path("trades")
  GetbtcTransaction[] getTransactions(@QueryParam("currency") String currency) throws IOException;

  /** klines?market=eth_btc&type=1min&size=1&assist=cny Returns Transactions by currency */
  @GET
  @Path("klines")
  Object getKlines(
      @QueryParam("market") String market,
      @QueryParam("type") String type,
      @QueryParam("size") String size,
      @QueryParam("assist") String assist)
      throws IOException;

  /** klines?market=eth_btc&type=1min&size=1&assist=cny Returns Transactions by currency */
  @GET
  @Path("markets")
  Object getMarkets() throws IOException;
}
