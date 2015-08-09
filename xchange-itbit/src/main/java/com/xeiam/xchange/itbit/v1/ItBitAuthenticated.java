package com.xeiam.xchange.itbit.v1;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import com.xeiam.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitOrder;
import com.xeiam.xchange.itbit.v1.dto.trade.ItBitPlaceOrderRequest;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface ItBitAuthenticated extends ItBit {

  @GET
  @Path("/markets/{ident}{currency}/ticker")
  ItBitTicker getTicker(@PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency) throws IOException;

  @GET
  @Path("wallets?userId={userId}")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitAccountInfoReturn[] getInfo(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("userId") String userId) throws IOException;

  @GET
  @Path("wallets/{walletId}")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitAccountInfoReturn getWallet(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("walletId") String walletId) throws IOException;

  @GET
  @Path("wallets/{walletId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder[] getOrders(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @QueryParam("instrument") String instrument,
      @QueryParam("page") String page, @QueryParam("perPage") String perPage, @QueryParam("status") String status,
      @PathParam("walletId") String walletId) throws IOException;

  @GET
  @Path("wallets/{walletId}/orders/{orderId}")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder getOrder(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("walletId") String walletId,
      @PathParam("orderId") String orderId) throws IOException;

  @POST
  @Path("wallets/{walletId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder postOrder(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("walletId") String walletId,
      ItBitPlaceOrderRequest request) throws IOException;

  /** Returns empty body, return object is always null */
  @DELETE
  @Path("wallets/{walletId}/orders/{orderId}")
  @Consumes(MediaType.APPLICATION_JSON)
  Object cancelOrder(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("walletId") String walletId,
      @PathParam("orderId") String orderId) throws IOException;

  @POST
  @Path("wallets/{walletId}/cryptocurrency_withdrawals")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder requestWithdrawal(@HeaderParam("Authorization") ParamsDigest signer, @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory, @PathParam("walletId") String walletId,
      ItBitPlaceOrderRequest request) throws IOException;

}
