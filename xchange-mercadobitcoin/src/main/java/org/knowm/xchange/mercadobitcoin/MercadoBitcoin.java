package org.knowm.xchange.mercadobitcoin;

import java.io.IOException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
  @Path("/{baseCurrency}/orderbook/")
  MercadoBitcoinOrderBook getOrderBook(@PathParam("baseCurrency") String baseCurrency)
      throws IOException;

  @GET
  @Path("/{baseCurrency}/ticker/")
  MercadoBitcoinTicker getTicker(@PathParam("baseCurrency") String baseCurrency) throws IOException;

  @GET
  @Path("/{baseCurrency}/trades/")
  MercadoBitcoinTransaction[] getTransactions(@PathParam("baseCurrency") String baseCurrency)
      throws IOException;

  @GET
  @Path("/{baseCurrency}/trades/{start_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactions(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("start_timestamp") Long startTimestamp)
      throws IOException;

  @GET
  @Path("/{baseCurrency}/trades/{start_timestamp: [0-9]}/{end_timestamp: [0-9]}/")
  MercadoBitcoinTransaction[] getTransactions(
      @PathParam("baseCurrency") String baseCurrency,
      @PathParam("start_timestamp") Long startTimestamp,
      @PathParam("end_timestamp") Long endTimestamp)
      throws IOException;
}