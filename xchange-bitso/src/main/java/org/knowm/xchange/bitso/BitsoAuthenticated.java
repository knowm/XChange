package org.knowm.xchange.bitso;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.account.BitsoDepositAddress;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import org.knowm.xchange.bitso.service.BitsoDigest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz See https://www.bitso.net/api/ for up-to-date docs., Piotr Ładyżyński */
@Path("v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitsoAuthenticated {

  @POST
  @Path("open_orders/")
  BitsoOrder[] getOpenOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitsoException, IOException;

  @POST
  @Path("buy/")
  BitsoOrder buy(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitsoException, IOException;

  @POST
  @Path("sell/")
  BitsoOrder sell(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitsoException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  boolean cancelOrder(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") String orderId)
      throws BitsoException, IOException;

  @POST
  @Path("user_transactions/")
  BitsoUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions)
      throws BitsoException, IOException;

  @POST
  @Path("user_transactions/")
  BitsoUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions,
      @FormParam("offset") long offset,
      @FormParam("sort") String sort)
      throws BitsoException, IOException;

  @POST
  @Path("balance/")
  BitsoBalance getBalance(
      @FormParam("key") String apiKey,
      @FormParam("signature") BitsoDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitsoException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  BitsoDepositAddress getBitcoinDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") BitsoDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitsoException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  String withdrawBitcoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") BitsoDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitsoException, IOException;

  @POST
  @Path("ripple_withdrawal/")
  String withdrawToRipple(
      @FormParam("key") String apiKey,
      @FormParam("signature") BitsoDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("address") String rippleAddress)
      throws BitsoException, IOException;
}
