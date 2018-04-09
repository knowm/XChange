package org.knowm.xchange.mercadobitcoin;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinOrderBook;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTransaction;

/**
 * @author Matija Mazi
 * @author Felipe Micaroni Lalli - See https://www.mercadobitcoin.net/api/ and
 *     https://www.mercadobitcoin.net/trade-api/ for up-to-date docs.
 * @see org.knowm.xchange.mercadobitcoin.MercadoBitcoinAuthenticated
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface MercadoBitcoin {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("/v1/orderbook/")
  MercadoBitcoinOrderBook getOrderBookBTC() throws IOException;

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("/v1/orderbook_litecoin/")
  MercadoBitcoinOrderBook getOrderBookLTC() throws IOException;

  @GET
  @Path("/v2/ticker/")
  MercadoBitcoinTicker getTickerBTC() throws IOException;

  @GET
  @Path("/v2/ticker_litecoin/")
  MercadoBitcoinTicker getTickerLTC() throws IOException;

  @GET
  @Path("/v1/trades/")
  MercadoBitcoinTransaction[] getTransactionsBTC() throws IOException;

  @GET
  @Path("/v1/trades_litecoin/")
  MercadoBitcoinTransaction[] getTransactionsLTC() throws IOException;

  @GET
  @Path("/v1/trades/{start_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactionsBTC(@PathParam("start_timestamp") Long startTimestamp)
      throws IOException;

  @GET
  @Path("/v1/trades_litecoin/{start_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactionsLTC(@PathParam("start_timestamp") Long startTimestamp)
      throws IOException;

  @GET
  @Path("/v1/trades/{start_timestamp: [0-9]}/{end_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactionsBTC(
      @PathParam("start_timestamp") Long startTimestamp,
      @PathParam("end_timestamp") Long endTimestamp)
      throws IOException;

  @GET
  @Path("/v1/trades_litecoin/{start_timestamp: [0-9]}/{end_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactionsLTC(
      @PathParam("start_timestamp") Long startTimestamp,
      @PathParam("end_timestamp") Long endTimestamp)
      throws IOException;
}
