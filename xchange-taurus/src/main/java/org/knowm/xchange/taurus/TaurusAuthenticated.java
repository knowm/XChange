package org.knowm.xchange.taurus;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.taurus.dto.TaurusException;
import org.knowm.xchange.taurus.dto.account.TaurusBalance;
import org.knowm.xchange.taurus.dto.trade.TaurusOrder;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface TaurusAuthenticated extends Taurus {

  @POST
  @Path("open_orders/")
  TaurusOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws TaurusException, IOException;

  @POST
  @Path("buy/")
  TaurusOrder buy(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws TaurusException, IOException;

  @POST
  @Path("sell/")
  TaurusOrder sell(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price) throws TaurusException, IOException;

  // todo: market order

  /**
   * @return true if order has been canceled.
   */
  @POST
  @Path("cancel_order/")
  boolean cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("id") String orderId) throws TaurusException, IOException;

  @POST
  @Path("balance/")
  TaurusBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws TaurusException, IOException;

  @POST
  @Path("user_transactions/")
  TaurusUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("limit") Integer limit, @FormParam("offset") Integer offset,
      @FormParam("sort") TradeHistoryParamsSorted.Order sort) throws TaurusException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  String getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws TaurusException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  String withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address) throws TaurusException, IOException;
}
