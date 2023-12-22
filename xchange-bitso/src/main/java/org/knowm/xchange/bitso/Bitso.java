package org.knowm.xchange.bitso;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTransaction;

/**
 * @author Piotr Ładyżyński
 */
@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitso {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("order_book/")
  BitsoOrderBook getOrderBook() throws BitsoException, IOException;

  @GET
  @Path("ticker/?book={currency}")
  BitsoTicker getTicker(@PathParam("currency") String currency) throws BitsoException, IOException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/")
  BitsoTransaction[] getTransactions() throws BitsoException, IOException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/")
  BitsoTransaction[] getTransactions(@QueryParam("time") String time)
      throws BitsoException, IOException;
}
