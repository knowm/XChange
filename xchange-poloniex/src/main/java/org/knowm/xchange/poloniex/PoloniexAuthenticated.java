package org.knowm.xchange.poloniex;

import org.knowm.xchange.poloniex.dto.account.PoloniexBalance;
import org.knowm.xchange.poloniex.dto.account.PoloniexLoan;
import org.knowm.xchange.poloniex.dto.account.WithdrawalResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexOpenOrder;
import org.knowm.xchange.poloniex.dto.trade.PoloniexTradeResponse;
import org.knowm.xchange.poloniex.dto.trade.PoloniexUserTrade;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
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
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("account") String account) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, PoloniexLoan[]> returnActiveLoans(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, String> returnDepositAddresses(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, PoloniexOpenOrder[]> returnOpenOrders(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  PoloniexUserTrade[] returnTradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair, @FormParam("start") Long startTime,
      @FormParam("end") Long endTime) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, PoloniexUserTrade[]> returnTradeHistory(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currencyPair") String currencyPair, @FormParam("start") Long startTime,
      @FormParam("end") Long endTime, @FormParam("ignore") String overload) throws PoloniexException, IOException;

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
      @FormParam("currencyPair") String currencyPair) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  WithdrawalResponse withdraw(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("currency") String currency, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address, @FormParam("paymentId") @Nullable String paymentId) throws PoloniexException, IOException;

  @POST
  @FormParam("command")
  HashMap<String, String> returnFeeInfo(@HeaderParam("Key") String apiKey, @HeaderParam("Sign") ParamsDigest signature,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws PoloniexException, IOException;
}
