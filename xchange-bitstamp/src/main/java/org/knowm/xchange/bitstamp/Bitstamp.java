package org.knowm.xchange.bitstamp;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitstamp.dto.marketdata.BitstampOrderBook;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTicker;
import org.knowm.xchange.bitstamp.dto.marketdata.BitstampTransaction;

/**
 * @author Matija Mazi See https://www.bitstamp.net/api/ for up-to-date docs.
 * @deprecated Use {@link BitstampV2} instead.
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Deprecated
public interface Bitstamp {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("order_book/")
  BitstampOrderBook getOrderBook() throws IOException;

  @GET
  @Path("ticker/")
  BitstampTicker getTicker() throws IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions/")
  BitstampTransaction[] getTransactions() throws IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions/")
  BitstampTransaction[] getTransactions(@QueryParam("time") String time) throws IOException;

}
