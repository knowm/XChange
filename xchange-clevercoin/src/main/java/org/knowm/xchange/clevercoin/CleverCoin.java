package org.knowm.xchange.clevercoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.clevercoin.dto.marketdata.CleverCoinOrderBook;
import org.knowm.xchange.clevercoin.dto.marketdata.CleverCoinTicker;
import org.knowm.xchange.clevercoin.dto.marketdata.CleverCoinTransaction;

/**
 * @author Karsten Nilsen See https://github.com/CleverCoin/clever-api-client/ for up-to-date info.
 */
@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CleverCoin {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("orderbook")
  public CleverCoinOrderBook getOrderBook() throws IOException;

  @GET
  @Path("ticker")
  public CleverCoinTicker getTicker() throws IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions")
  public CleverCoinTransaction[] getTransactions() throws IOException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("transactions")
  public CleverCoinTransaction[] getTransactions(@QueryParam("since") String since) throws IOException;

}
