package org.knowm.xchange.gatecoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.gatecoin.dto.GatecoinException;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;

/**
 * @author sumedha. See https://www.gatecoin.com/api/ for up-to-date docs.
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface Gatecoin {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
   */
  @GET
  @Path("public/MarketDepth/{CurrencyPair}")
  GatecoinDepthResult getOrderBook(@PathParam("CurrencyPair") String CurrencyPair) throws IOException, GatecoinException;

  @GET
  @Path("public/livetickers/")
  GatecoinTickerResult getTicker() throws IOException, GatecoinException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("public/transactions/{CurrencyPair}")
  GatecoinTransactionResult getTransactions(@PathParam("CurrencyPair") String CurrencyPair) throws IOException, GatecoinException;

  /**
   * Returns descending list of transactions.
   */
  @GET
  @Path("public/transactions/{CurrencyPair}")
  GatecoinTransactionResult getTransactions(@PathParam("CurrencyPair") String CurrencyPair, @QueryParam("Count") int Count,
      @QueryParam("TransactionId") long TransactionId) throws IOException, GatecoinException;
}
