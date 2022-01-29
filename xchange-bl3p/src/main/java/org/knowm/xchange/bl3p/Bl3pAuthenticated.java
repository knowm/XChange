package org.knowm.xchange.bl3p;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bl3p.dto.Bl3pUserTransactions;
import org.knowm.xchange.bl3p.dto.account.Bl3pAccountInfo;
import org.knowm.xchange.bl3p.dto.account.Bl3pNewDepositAddress;
import org.knowm.xchange.bl3p.dto.account.Bl3pWithdrawFunds;
import org.knowm.xchange.bl3p.dto.trade.Bl3pCancelOrder;
import org.knowm.xchange.bl3p.dto.trade.Bl3pGetOrder;
import org.knowm.xchange.bl3p.dto.trade.Bl3pNewOrder;
import org.knowm.xchange.bl3p.dto.trade.Bl3pOpenOrders;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Defines methods which need authentication
 *
 * <p>GENMKT is presumably short for GENERIC MARKET.
 */
@Path("1")
@Produces(MediaType.APPLICATION_JSON)
public interface Bl3pAuthenticated extends Bl3p {

  /**
   * Get account info and balance
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @return
   * @throws IOException
   */
  @GET
  @Path("/GENMKT/money/info")
  Bl3pAccountInfo getAccountInfo(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  /**
   * Create a new deposit address
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currency Currency (Can be: 'BTC')
   * @return
   * @throws IOException
   */
  @GET
  @Path("/GENMKT/money/new_deposit_address")
  Bl3pNewDepositAddress createNewDepositAddress(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency)
      throws IOException;

  /**
   * Get all open orders
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currencyPair
   * @return
   */
  @GET
  @Path("/{currencyPair}/money/orders")
  Bl3pOpenOrders getOpenOrders(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currencyPair") String currencyPair);

  /**
   * Create a market order
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currencyPair
   * @param type
   * @param amountInt
   * @param feeCurrency
   * @return
   */
  @POST
  @Path("/{currencyPair}/money/order/add")
  Bl3pNewOrder createMarketOrder(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currencyPair") String currencyPair,
      @FormParam("type") String type,
      @FormParam("amount_int") long amountInt,
      @FormParam("fee_currency") String feeCurrency);

  /**
   * Create a limit order
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currencyPair
   * @param type
   * @param amountInt
   * @param priceInt
   * @param feeCurrency
   * @return
   */
  @POST
  @Path("/{currencyPair}/money/order/add")
  Bl3pNewOrder createLimitOrder(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currencyPair") String currencyPair,
      @FormParam("type") String type,
      @FormParam("amount_int") long amountInt,
      @FormParam("price_int") long priceInt,
      @FormParam("fee_currency") String feeCurrency);

  /**
   * Cancel given order
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currencyPair
   * @param orderId
   * @return
   */
  @POST
  @Path("/{currencyPair}/money/order/cancel")
  Bl3pCancelOrder cancelOrder(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currencyPair") String currencyPair,
      @FormParam("order_id") String orderId);

  /**
   * Get a specific order
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currencyPair
   * @param orderId
   * @return
   */
  @GET
  @Path("{currencyPair}/money/order/result")
  Bl3pGetOrder getOrder(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currencyPair") String currencyPair,
      @FormParam("order_id") String orderId);

  /**
   * Get a list of transactions
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currency
   * @param type
   * @param page
   * @param recsPerPage
   * @return
   */
  @GET
  @Path("GENMKT/money/wallet/history")
  Bl3pUserTransactions getUserTransactions(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @PathParam("currency") String currency,
      @FormParam("type") String type,
      @FormParam("page") int page,
      @FormParam("recs_per_page") int recsPerPage);

  /**
   * Withdraw coins
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currency
   * @param address
   * @param extraFee
   * @param amount
   * @return
   */
  @POST
  @Path("GENMKT/money/withdraw")
  Bl3pWithdrawFunds withdrawCoins(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("address") String address,
      @FormParam("extra_fee") int extraFee,
      @FormParam("amount_int") long amount);

  /**
   * Withdraw euros
   *
   * @param restKey
   * @param restSign
   * @param nonce
   * @param currency
   * @param accountId
   * @param accountName
   * @param amount
   * @return
   */
  @POST
  @Path("GENMKT/money/withdraw")
  Bl3pWithdrawFunds withdrawEuros(
      @HeaderParam("Rest-Key") String restKey,
      @HeaderParam("Rest-Sign") ParamsDigest restSign,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("account_id") String accountId,
      @FormParam("account_name") String accountName,
      @FormParam("amount_int") long amount);
}
