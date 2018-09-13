package org.knowm.xchange.cryptonit2;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.account.*;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrder;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatusResponse;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Benedikt Bünz See https://www.bitstamp.net/api/ for up-to-date docs. */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
@Deprecated
public interface CryptonitAuthenticated {

  /**
   * @deprecated Use {@link CryptonitAuthenticatedV2#getOpenOrders(String, ParamsDigest,
   *     SynchronizedValueFactory, CryptonitV2.Pair)}.
   */
  @POST
  @Path("open_orders/")
  @Deprecated
  CryptonitOrder[] getOpenOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  /** @deprecated Use . */
  @POST
  @Path("buy/")
  @Deprecated
  CryptonitOrder buy(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws CryptonitException, IOException;

  /** @deprecated Use . */
  @POST
  @Path("sell/")
  @Deprecated
  CryptonitOrder sell(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("price") BigDecimal price)
      throws CryptonitException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_order/")
  boolean cancelOrder(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") int orderId)
      throws CryptonitException, IOException;

  /** @return true if order has been canceled. */
  @POST
  @Path("cancel_all_orders/")
  boolean cancelAllOrders(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("balance/")
  CryptonitBalance getBalance(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("user_transactions/")
  CryptonitUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions)
      throws CryptonitException, IOException;

  @POST
  @Path("user_transactions/")
  CryptonitUserTransaction[] getUserTransactions(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("limit") long numberOfTransactions,
      @FormParam("offset") long offset,
      @FormParam("sort") String sort)
      throws CryptonitException, IOException;

  @POST
  @Path("bitcoin_deposit_address/")
  CryptonitDepositAddress getBitcoinDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("bitcoin_withdrawal/")
  CryptonitWithdrawal withdrawBitcoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws CryptonitException, IOException;

  @POST
  @Path("eth_address/")
  CryptonitDepositAddress getEthereumDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("eth_withdrawal/")
  CryptonitWithdrawal withdrawEthereum(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws CryptonitException, IOException;

  @POST
  @Path("ltc_address/")
  CryptonitDepositAddress getLitecoinDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("ltc_withdrawal/")
  CryptonitWithdrawal withdrawLitecoin(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws CryptonitException, IOException;

  @POST
  @Path("unconfirmed_btc/")
  DepositTransaction[] getUnconfirmedDeposits(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("withdrawal_requests/")
  WithdrawalRequest[] getWithdrawalRequests(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  /**
   * Note that due to a bug on Cryptonit's side, withdrawal always fails if two-factor
   * authentication is enabled for the account.
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
      throws CryptonitException, IOException;

  @POST
  @Path("ripple_address/")
  CryptonitRippleDepositAddress getRippleDepositAddress(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("order_status/")
  public CryptonitOrderStatusResponse getOrderStatus(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") long orderId)
      throws CryptonitException, IOException;
}
