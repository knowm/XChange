package com.xeiam.xchange.gatecoin;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTickerResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;
import com.xeiam.xchange.gatecoin.dto.marketdata.Results.GatecoinTransactionResult;
import javax.ws.rs.PathParam;

/**
 * @author sumedha.  See https://www.gatecoin.com/api/ for up-to-date docs.
 */
@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface Gatecoin {

  /**
   * Returns "bids" and "asks". Each is a list of open orders and each order is represented as a list of price and amount.
     * @param CurrencyPair
     * @return 
     * @throws java.io.IOException
   */
  @GET
  @Path("public/MarketDepth/{CurrencyPair}")
  public GatecoinDepthResult getOrderBook(@PathParam("CurrencyPair") String CurrencyPair) throws IOException;

@GET
  @Path("public/livetickers/")
  public GatecoinTickerResult getTicker() throws IOException;
  /**
   * Returns descending list of transactions.
   *  @param CurrencyPair
     * @return 
     * @throws java.io.IOException
   */
  @GET
  @Path("public/transactions/{CurrencyPair}")
  public GatecoinTransactionResult getTransactions(@PathParam("CurrencyPair") String CurrencyPair) throws IOException;

  /**
   * Returns descending list of transactions.
   *  @param CurrencyPair
     * @param Count
     * @return 
     * @throws java.io.IOException
   */
  @GET
  @Path("public/transactions/{CurrencyPair}")
  public GatecoinTransactionResult getTransactions(@PathParam("CurrencyPair") String CurrencyPair,@QueryParam("Count") int Count, @QueryParam("TransactionId") long TransactionId) throws IOException;
  
}
