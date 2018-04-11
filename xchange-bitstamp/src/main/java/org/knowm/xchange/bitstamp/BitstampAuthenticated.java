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
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrderStatusResponse;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs. */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Deprecated
public interface BitstampAuthenticated {

  /**
   * @deprecated Use {@link BitstampAuthenticatedV2#getOpenOrders(String, ParamsDigest,
   *     SynchronizedValueFactory, BitstampV2.Pair)}.
   */
  @POST
  @Path("open_orders/")
  @Deprecated
  BitstampOrder[] getOpenOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  /** @deprecated Use . */
  @POST
  @Path("buy/")
  @Deprecated
  BitstampOrder buy(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  /** @deprecated Use . */
  @POST
  @Path("sell/")
  @Deprecated
  BitstampOrder sell(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws BitstampException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  boolean cancelOrder(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") int orderId)
      throws BitstampException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_all_orders/")
  boolean cancelAllOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("v2/balance/")
  BitstampBalance getBalance(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  BitstampUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions)
      throws BitstampException, IOException;

  @POST
  @Path("user_transactions/")
  BitstampUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions,
      @FormParam("offset") long offset,
      @FormParam("sort") String sort)
      throws BitstampException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  BitstampDepositAddress getBitcoinDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  BitstampWithdrawal withdrawBitcoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("v2/eth_address/")
  BitstampDepositAddress getEthereumDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("v2/eth_withdrawal/")
  BitstampWithdrawal withdrawEthereum(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("v2/ltc_address/")
  BitstampDepositAddress getLitecoinDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("v2/ltc_withdrawal/")
  BitstampWithdrawal withdrawLitecoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws BitstampException, IOException;

  @POST
  @Path("unconfirmed_btc/")
  DepositTransaction[] getUnconfirmedDeposits(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("withdrawal_requests/")
  WithdrawalRequest[] getWithdrawalRequests(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  /**
   * Note that due to a bug on Bitstamp's side, withdrawal always fails if two-factor authentication
   * is enabled for the account.
   */
  @POST
  @Path("ripple_withdrawal/")
  boolean withdrawToRipple(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("currency") String currency,
      @FormParam("address") String rippleAddress)
      throws BitstampException, IOException;

  @POST
  @Path("ripple_address/")
  BitstampRippleDepositAddress getRippleDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws BitstampException, IOException;

  @POST
  @Path("order_status/")
  public BitstampOrderStatusResponse getOrderStatus(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") long orderId)
      throws BitstampException, IOException;
}
