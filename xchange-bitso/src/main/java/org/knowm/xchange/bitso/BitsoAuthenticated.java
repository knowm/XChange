package org.knowm.xchange.bitso;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bitso.dto.BitsoException;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.account.BitsoDepositAddress;
import org.knowm.xchange.bitso.dto.trade.BitsoAllOrders;
import org.knowm.xchange.bitso.dto.trade.BitsoCacleOrderResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.bitso.dto.trade.BitsoOrderResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoPlaceOrder;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import org.knowm.xchange.bitso.service.BitsoDigest;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz See https://www.bitso.net/api/ for up-to-date docs., Piotr Ładyżyński */
@Path("v3")
// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitsoAuthenticated {

  @GET
  @Path("open_orders/")
  BitsoAllOrders getOpenOrders(@HeaderParam("Authorization") ParamsDigest signed)
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

  @POST
  @Path("/orders/")
  @Consumes(MediaType.APPLICATION_JSON)
  BitsoOrderResponse placeOrder(
      @HeaderParam("Authorization") String authorization, BitsoPlaceOrder bitsoPlaceOrder)
      throws BitsoException, IOException;

  @DELETE
  @Path("/orders/{oid}/")
  @Consumes(MediaType.APPLICATION_JSON)
  BitsoCacleOrderResponse cancelOrder(
      @HeaderParam("Authorization") String authorization, @PathParam("oid") String oid)
      throws BitsoException, IOException;

  @GET
  @Path("/orders/{oid}/")
  @Consumes(MediaType.APPLICATION_JSON)
  BitsoAllOrders getOrder(
      @HeaderParam("Authorization") String authorization, @PathParam("oid") String oid)
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

  @GET
  @Path("balance/")
  BitsoBalance getBalance(@HeaderParam("Authorization") String authorization)
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
