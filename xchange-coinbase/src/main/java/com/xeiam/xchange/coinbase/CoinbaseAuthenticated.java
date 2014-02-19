/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import si.mazi.rescu.ParamsDigest;

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
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
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
public interface CoinbaseAuthenticated extends Coinbase {

  @GET
  @Path("users")
  CoinbaseUsers getUsers(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("users/{userId}")
  CoinbaseUser updateUser(@PathParam("userId") String userId, CoinbaseUser user, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Path("tokens/redeem")
  CoinbaseBaseResponse redeemToken(@QueryParam("token_id") String tokenId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("account/balance")
  CoinbaseAmount getBalance(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("account/receive_address")
  CoinbaseAddress getReceiveAddress(@HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("account/generate_receive_address")
  CoinbaseAddress generateReceiveAddress(CoinbaseAddressCallback callbackUrl, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("account_changes")
  CoinbaseAccountChanges getAccountChanges(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("addresses")
  CoinbaseAddresses getAddresses(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @QueryParam("query") String query, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("contacts")
  CoinbaseContacts getContacts(@QueryParam("page") Integer page, @QueryParam("num_pages") Integer limit, @QueryParam("query") String query, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("transfers")
  CoinbaseTransfers getTransfers(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("transactions")
  CoinbaseTransactions getTransactions(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("transactions/{transactionId}")
  CoinbaseTransaction getTransactionDetails(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("transactions/request_money")
  CoinbaseTransaction requestMoney(CoinbaseTransaction transactionRequest, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("transactions/send_money")
  CoinbaseTransaction sendMoney(CoinbaseTransaction transactionRequest, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @PUT
  @Path("transactions/{transactionId}/resend_request")
  CoinbaseBaseResponse resendRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @PUT
  @Path("transactions/{transactionId}/complete_request")
  CoinbaseTransaction completeRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @DELETE
  @Path("transactions/{transactionId}/cancel_request")
  CoinbaseBaseResponse cancelRequest(@PathParam("transactionId") String transactionId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("buttons")
  CoinbaseButton createButton(CoinbaseButton button, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;

  @GET
  @Path("orders")
  CoinbaseOrders getOrders(@QueryParam("page") Integer page, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;

  @GET
  @Path("orders/{orderId}")
  CoinbaseOrder getOrder(@PathParam("orderId") String orderId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;

  @POST
  @Path("buttons/{code}/create_order")
  CoinbaseOrder createOrder(@PathParam("code") String code, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("orders")
  CoinbaseOrder createOrder(CoinbaseButton button, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;

  @GET
  @Path("recurring_payments")
  CoinbaseRecurringPayments getRecurringPayments(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("recurring_payments/{recurringPaymentId}")
  CoinbaseRecurringPayment getRecurringPayment(@PathParam("recurringPaymentId") String recurringPaymentId, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("subscribers")
  CoinbaseSubscriptions getsSubscriptions(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @GET
  @Path("subscribers/{subscriptionId}")
  CoinbaseSubscription getsSubscription(@PathParam("subscriptionId") String subscriptionId, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer,
      @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Path("buys")
  CoinbaseTransfer buy(@QueryParam("qty") String quantity, @QueryParam("agree_btc_amount_varies") boolean agreeBTCAmountVaries, @HeaderParam("ACCESS_KEY") String apiKey,
      @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce) throws IOException;

  @POST
  @Path("sells")
  CoinbaseTransfer sell(@QueryParam("qty") String quantity, @HeaderParam("ACCESS_KEY") String apiKey, @HeaderParam("ACCESS_SIGNATURE") ParamsDigest signer, @HeaderParam("ACCESS_NONCE") long nonce)
      throws IOException;
}
