package org.knowm.xchange.coinbase;

import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinbase.dto.CoinbaseBaseResponse;
import org.knowm.xchange.coinbase.dto.CoinbaseException;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddress;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddressCallback;
import org.knowm.xchange.coinbase.dto.account.CoinbaseAddresses;
import org.knowm.xchange.coinbase.dto.account.CoinbaseContacts;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayment;
import org.knowm.xchange.coinbase.dto.account.CoinbaseRecurringPayments;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransaction;
import org.knowm.xchange.coinbase.dto.account.CoinbaseTransactions;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUser;
import org.knowm.xchange.coinbase.dto.account.CoinbaseUsers;
import org.knowm.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseButton;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrder;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseOrders;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import org.knowm.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfer;
import org.knowm.xchange.coinbase.dto.trade.CoinbaseTransfers;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author jamespedwards42 */
@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseAuthenticated extends Coinbase {

  @GET
  @Path("users")
  CoinbaseUsers getUsers(
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @PUT
  @Path("users/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseUser updateUser(
      @PathParam("userId") String userId,
      CoinbaseUser user,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("tokens/redeem")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse redeemToken(
      @QueryParam("token_id") String tokenId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("account/balance")
  CoinbaseMoney getBalance(
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("account/receive_address")
  CoinbaseAddress getReceiveAddress(
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("account/generate_receive_address")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseAddress generateReceiveAddress(
      CoinbaseAddressCallback callbackUrl,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("account_changes")
  CoinbaseAccountChanges getAccountChanges(
      @QueryParam("page") Integer page,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("addresses")
  CoinbaseAddresses getAddresses(
      @QueryParam("page") Integer page,
      @QueryParam("limit") Integer limit,
      @QueryParam("query") String query,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("contacts")
  CoinbaseContacts getContacts(
      @QueryParam("page") Integer page,
      @QueryParam("num_pages") Integer limit,
      @QueryParam("query") String query,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("transfers")
  CoinbaseTransfers getTransfers(
      @QueryParam("page") Integer page,
      @QueryParam("limit") Integer limit,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("transactions")
  CoinbaseTransactions getTransactions(
      @QueryParam("page") Integer page,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("transactions/{transactionId}")
  CoinbaseTransaction getTransactionDetails(
      @PathParam("transactionId") String transactionId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("transactions/request_money")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseTransaction requestMoney(
      CoinbaseTransaction transactionRequest,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("transactions/send_money")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseTransaction sendMoney(
      CoinbaseTransaction transactionRequest,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @PUT
  @Path("transactions/{transactionId}/resend_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse resendRequest(
      @PathParam("transactionId") String transactionId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @PUT
  @Path("transactions/{transactionId}/complete_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransaction completeRequest(
      @PathParam("transactionId") String transactionId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @DELETE
  @Path("transactions/{transactionId}/cancel_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse cancelRequest(
      @PathParam("transactionId") String transactionId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("buttons")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseButton createButton(
      CoinbaseButton button,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("orders")
  CoinbaseOrders getOrders(
      @QueryParam("page") Integer page,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("orders/{orderId}")
  CoinbaseOrder getOrder(
      @PathParam("orderId") String orderId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("buttons/{code}/create_order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseOrder createOrder(
      @PathParam("code") String code,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseOrder createOrder(
      CoinbaseButton button,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("recurring_payments")
  CoinbaseRecurringPayments getRecurringPayments(
      @QueryParam("page") Integer page,
      @QueryParam("limit") Integer limit,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("recurring_payments/{recurringPaymentId}")
  CoinbaseRecurringPayment getRecurringPayment(
      @PathParam("recurringPaymentId") String recurringPaymentId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("subscribers")
  CoinbaseSubscriptions getsSubscriptions(
      @QueryParam("page") Integer page,
      @QueryParam("limit") Integer limit,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @GET
  @Path("subscribers/{subscriptionId}")
  CoinbaseSubscription getsSubscription(
      @PathParam("subscriptionId") String subscriptionId,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("buys")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransfer buy(
      @QueryParam("qty") String quantity,
      @QueryParam("agree_btc_amount_varies") boolean agreeBTCAmountVaries,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;

  @POST
  @Path("sells")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransfer sell(
      @QueryParam("qty") String quantity,
      @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce)
      throws IOException, CoinbaseException;
}
