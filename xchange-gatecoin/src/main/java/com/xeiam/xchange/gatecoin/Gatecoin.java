package com.xeiam.xchange.gatecoin;

import com.xeiam.xchange.gatecoin.dto.GatecoinException;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * @author sumedha.  See https://www.gatecoin.com/api/ for up-to-date docs.
 */
@Path("api")
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
  GatecoinTransactionResult getTransactions(
      @PathParam("CurrencyPair") String CurrencyPair,
      @QueryParam("Count") int Count,
      @QueryParam("TransactionId") long TransactionId
  ) throws IOException, GatecoinException;
}
