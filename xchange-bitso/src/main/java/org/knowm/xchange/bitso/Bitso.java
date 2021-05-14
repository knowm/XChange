package org.knowm.xchange.bitso;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTransaction;
import org.knowm.xchange.bitso.dto.trade.BitsoTrades;

/** @author Ravi Pandit */
@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitso {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("order_book/")
  BitsoOrderBook getOrderBook(@QueryParam("book") String book) throws BitsoException, IOException;

  @GET
  @Path("ticker/?book={currency}")
  BitsoTicker getTicker(@PathParam("currency") String currency) throws BitsoException, IOException;

  @GET
  @Path("trades/?book={currency}")
  BitsoTrades getTrades(@PathParam("currency") String currency) throws BitsoException, IOException;

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
