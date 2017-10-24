package org.knowm.xchange.quadrigacx;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.quadrigacx.dto.QuadrigaCxException;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxBalance;
import org.knowm.xchange.quadrigacx.dto.account.QuadrigaCxDepositAddress;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxOrder;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxUserTransaction;
import org.knowm.xchange.quadrigacx.service.QuadrigaCxDigest;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v2")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface QuadrigaCxAuthenticated {

  @POST
  @Path("open_orders")
  QuadrigaCxOrder[] getOpenOrders(@FormParam("book") String book, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws QuadrigaCxException, IOException;

  @POST
  @Path("buy")
  QuadrigaCxOrder buy(@FormParam("book") String book, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws QuadrigaCxException, IOException;

  @POST
  @Path("sell")
  QuadrigaCxOrder sell(@FormParam("book") String book, @FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws QuadrigaCxException, IOException;

  /**
   * @return true if order has been canceled.
   */
  @POST
  @Path("cancel_order")
  boolean cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("id") String orderId) throws QuadrigaCxException, IOException;

  @POST
  @Path("user_transactions")
  QuadrigaCxUserTransaction[] getUserTransactions(@FormParam("book") String book, @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer, @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") Long numberOfTransactions, @FormParam("offset") Long offset,
      @FormParam("sort") String sort) throws QuadrigaCxException, IOException;

  @POST
  @Path("balance")
  QuadrigaCxBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") QuadrigaCxDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws QuadrigaCxException, IOException;

  @POST
  @Path("bitcoin_deposit_address")
  QuadrigaCxDepositAddress getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") QuadrigaCxDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws QuadrigaCxException, IOException;

  @POST
  @Path("ether_deposit_address")
  QuadrigaCxDepositAddress getEtherDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") QuadrigaCxDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws QuadrigaCxException, IOException;

  @POST
  @Path("bitcoin_withdrawal")
  String withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") QuadrigaCxDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws QuadrigaCxException, IOException;

  @POST
  @Path("ether_withdrawal")
  String withdrawEther(@FormParam("key") String apiKey, @FormParam("signature") QuadrigaCxDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws QuadrigaCxException, IOException;
}
