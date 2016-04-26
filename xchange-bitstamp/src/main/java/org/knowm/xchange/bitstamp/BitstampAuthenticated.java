package org.knowm.xchange.bitstamp;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.account.BitstampBalance;
import org.knowm.xchange.bitstamp.dto.account.BitstampDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampRippleDepositAddress;
import org.knowm.xchange.bitstamp.dto.account.BitstampWithdrawal;
import org.knowm.xchange.bitstamp.dto.account.DepositTransaction;
import org.knowm.xchange.bitstamp.dto.account.WithdrawalRequest;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs.
 */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface BitstampAuthenticated extends Bitstamp {

  @POST
  @Path("v2/open_orders/btcusd/")
  public BitstampOrder[] getOpenOrdersUsd(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;

  @POST
  @Path("v2/open_orders/btceur/")
  public BitstampOrder[] getOpenOrdersEur(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce) throws BitstampException, IOException;
  
  @POST
  @Path("v2/buy/btcusd/")
  public BitstampOrder buyUsd(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  @POST
  @Path("v2/buy/btceur/")
  public BitstampOrder buyEur(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  @POST
  @Path("v2/sell/btcusd/")
  public BitstampOrder sellUsd(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce, @FormParam("amount") BigDecimal amount, @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  @POST
  @Path("v2/sell/btceur/")
  public BitstampOrder sellEur(@FormParam("key") String apiKey, @FormParam("signature") ParamsDigest signer,
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
