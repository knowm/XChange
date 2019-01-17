package org.knowm.xchange.cryptonit2;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.cryptonit2.dto.CryptonitException;
import org.knowm.xchange.cryptonit2.dto.account.*;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitOrderStatusResponse;
import org.knowm.xchange.cryptonit2.dto.trade.CryptonitUserTransaction;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author yurivin see api spcification https://dev-dev.dyn.cryptonit.net/en/api */
@Path("api")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface CryptonitAuthenticated {

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
  @Path("withdrawal_requests/")
  WithdrawalRequest[] getWithdrawalRequests(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws CryptonitException, IOException;

  @POST
  @Path("order_status/")
  CryptonitOrderStatusResponse getOrderStatus(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("id") long orderId)
      throws CryptonitException, IOException;

  @POST
  @Path("crypto_withdrawal/")
  CryptonitWithdrawal cryptoWithdrawal(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address,
      @FormParam("password") String password)
      throws CryptonitException, IOException;

  @POST
  @Path("fiat_withdrawal/")
  CryptonitWithdrawal fiatWithdrawal(
      @FormParam("key") String apiKey,
      @FormParam("signature") ParamsDigest signer,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("currency") String currency,
      @FormParam("amount") BigDecimal amount,
      @FormParam("method") Integer method,
      @FormParam("password") String password,
      @FormParam("transferDetails") String transferDetails)
      throws CryptonitException, IOException;
}
