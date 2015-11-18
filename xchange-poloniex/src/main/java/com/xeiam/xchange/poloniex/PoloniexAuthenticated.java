package com.xeiam.xchange.poloniex;

import com.xeiam.xchange.poloniex.dto.account.PoloniexBalance;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexTradeResponse;
import com.xeiam.xchange.poloniex.dto.trade.PoloniexUserTrade;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Zach Holmes
 */
@Path("tradingApi")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public interface PoloniexAuthenticated {

  @POST
  @FormParam("command")
  HashMap<String, PoloniexBalance> returnCompleteBalances(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce);

  @POST
  @FormParam("command")
  HashMap<String, String> returnDepositAddresses(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce);

  @POST
  @FormParam("command")
  HashMap<String, PoloniexOpenOrder[]> returnOpenOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair) throws IOException;

  @POST
  @FormParam("command")
  PoloniexUserTrade[] returnTradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair, @FormParam("start") Long startTime,
      @FormParam("end") Long endTime) throws IOException;

  @POST
  @FormParam("command")
  HashMap<String, PoloniexUserTrade[]> returnTradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair, @FormParam("start") Long startTime,
      @FormParam("end") Long endTime, @FormParam("ignore") String overload) throws IOException;

  @POST
  @FormParam("command")
  PoloniexTradeResponse buy(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") String amount, @FormParam("rate") String rate,
      @FormParam("currencyPair") String currencyPair) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  PoloniexTradeResponse sell(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") String amount, @FormParam("rate") String rate,
      @FormParam("currencyPair") String currencyPair) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, String> cancelOrder(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("orderNumber") String orderNumber,
      @FormParam("currencyPair") String currencyPair) throws IOException;
}
