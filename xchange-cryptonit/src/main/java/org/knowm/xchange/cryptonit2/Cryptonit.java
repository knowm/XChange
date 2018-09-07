package org.knowm.xchange.cryptonit2;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitOrderBook;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTicker;
import org.knowm.xchange.cryptonit2.dto.marketdata.CryptonitTransaction;

/**
 * @author Matija Mazi See https://www.cryptonit.net/api/ for up-to-date docs.
 * @deprecated Use {@link CryptonitV2} instead.
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
@Deprecated
public interface Cryptonit {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a
   * list of price and amount.
   */
  @GET
  @Path("order_book/")
  CryptonitOrderBook getOrderBook() throws IOException;

  @GET
  @Path("ticker/")
  CryptonitTicker getTicker() throws IOException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/")
  CryptonitTransaction[] getTransactions() throws IOException;

  /** Returns descending list of transactions. */
  @GET
  @Path("transactions/")
  CryptonitTransaction[] getTransactions(@QueryParam("time") String time) throws IOException;
}
