package org.knowm.xchange.exx;

import java.io.IOException;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.exx.dto.marketdata.EXXOrderbook;
import org.knowm.xchange.exx.dto.marketdata.EXXTicker;
import org.knowm.xchange.exx.dto.marketdata.EXXTickerResponse;
import org.knowm.xchange.exx.dto.marketdata.EXXTransaction;

@Path("data/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface EXX {

  @GET
  @Path("pubticker/btcusd")
  /**
   * Test connectivity to the Rest API. BTC/USD
   *
   * @return
   * @throws IOException
   */
  String getTickerString() throws IOException;

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("depth")
  EXXOrderbook getOrderBook(@QueryParam("currency") String currency) throws IOException;

  /** Returns ticker by currency */
  @GET
  @Path("ticker")
  EXXTickerResponse getTicker(@QueryParam("currency") String currency) throws IOException;

  /** Returns tickers */
  @GET
  @Path("tickers")
  // Map<String, List<Object>> getTickers() throws IOException;
  Map<String, EXXTicker> getTickers() throws IOException;

  /** Returns Transactions by currency */
  @GET
  @Path("trades")
  EXXTransaction[] getTransactions(@QueryParam("currency") String currency) throws IOException;

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
