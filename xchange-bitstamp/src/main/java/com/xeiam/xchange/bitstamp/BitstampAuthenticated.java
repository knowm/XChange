package com.xeiam.xchange.bitstamp;

import com.xeiam.xchange.bitstamp.dto.BitstampException;
import com.xeiam.xchange.bitstamp.dto.account.*;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticated extends Bitstamp {

  @POST
  @Path("open_orders/")
  public BitstampOrder[] getOpenOrders(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  @POST
  @Path("buy/")
  public BitstampOrder buy(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  @POST
  @Path("sell/")
  public BitstampOrder sell(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  /**
   * @return true if order has been canceled.
   */
  @POST
  @Path("cancel_order/")
  public boolean cancelOrder(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("id") int orderId) throws BitstampException, IOException;

  @POST
  @Path("balance/")
  public BitstampBalance getBalance(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("limit") long numberOfTransactions) throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  public BitstampUserTransaction[] getUserTransactions(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("limit") long numberOfTransactions, @FormParam("offset") long offset,
      @FormParam("sort") String sort) throws BitstampException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  public BitstampDepositAddress getBitcoinDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  public BitstampWithdrawal withdrawBitcoin(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("unconfirmed_btc/")
  public DepositTransaction[] getUnconfirmedDeposits(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  @POST
  @Path("withdrawal_requests/")
  public WithdrawalRequest[] getWithdrawalRequests(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  /**
   * Note that due to a bug on Bitstamp's side, withdrawal always fails if two-factor authentication is enabled for the account.
   */
  @POST
  @Path("ripple_withdrawal/")
  public boolean withdrawToRipple(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("currency") String currency,
      @FormParam("address") String rippleAddress) throws BitstampException, IOException;

  @POST
  @Path("ripple_address/")
  public BitstampRippleDepositAddress getRippleDepositAddress(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

}
