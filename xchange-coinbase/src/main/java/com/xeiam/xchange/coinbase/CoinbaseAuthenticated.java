package com.xeiam.xchange.coinbase;

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

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.coinbase.dto.CoinbaseBaseResponse;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddressCallback;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddresses;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseContacts;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseRecurringPayment;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseRecurringPayments;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransaction;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseTransactions;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUser;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseUsers;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseButton;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrder;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseOrders;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscription;
import com.xeiam.xchange.coinbase.dto.merchant.CoinbaseSubscriptions;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;

/**
 * @author jamespedwards42
 */
@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CoinbaseAuthenticated extends Coinbase {

  @GET
  @Path("users")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseUsers getUsers(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @PUT
  @Path("users/{userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseUser updateUser(@PathParam("userId") String userId, CoinbaseUser user, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("tokens/redeem")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse redeemToken(@QueryParam("token_id") String tokenId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("account/balance")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseMoney getBalance(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("account/receive_address")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseAddress getReceiveAddress(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("account/generate_receive_address")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseAddress generateReceiveAddress(CoinbaseAddressCallback callbackUrl, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("account_changes")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseAccountChanges getAccountChanges(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("addresses")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseAddresses getAddresses(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @QueryParam("query") String query,
      @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("contacts")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseContacts getContacts(@QueryParam("page") Integer page, @QueryParam("num_pages") Integer limit, @QueryParam("query") String query,
      @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("transfers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransfers getTransfers(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("transactions")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransactions getTransactions(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("transactions/{transactionId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransaction getTransactionDetails(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("transactions/request_money")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseTransaction requestMoney(CoinbaseTransaction transactionRequest, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("transactions/send_money")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseTransaction sendMoney(CoinbaseTransaction transactionRequest, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @PUT
  @Path("transactions/{transactionId}/resend_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse resendRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @PUT
  @Path("transactions/{transactionId}/complete_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransaction completeRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @DELETE
  @Path("transactions/{transactionId}/cancel_request")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseBaseResponse cancelRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("buttons")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseButton createButton(CoinbaseButton button, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("orders")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseOrders getOrders(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("orders/{orderId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseOrder getOrder(@PathParam("orderId") String orderId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("buttons/{code}/create_order")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseOrder createOrder(@PathParam("code") String code, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CoinbaseOrder createOrder(CoinbaseButton button, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("recurring_payments")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseRecurringPayments getRecurringPayments(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit,
      @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("recurring_payments/{recurringPaymentId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseRecurringPayment getRecurringPayment(@PathParam("recurringPaymentId") String recurringPaymentId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("subscribers")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseSubscriptions getsSubscriptions(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit,
      @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @GET
  @Path("subscribers/{subscriptionId}")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseSubscription getsSubscription(@PathParam("subscriptionId") String subscriptionId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("buys")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransfer buy(@QueryParam("qty") String quantity, @QueryParam("agree_btc_amount_varies") boolean agreeBTCAmountVaries,
      @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;

  @POST
  @Path("sells")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  CoinbaseTransfer sell(@QueryParam("qty") String quantity, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") SynchronizedValueFactory<Long> nonce) throws IOException;
}
