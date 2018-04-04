package org.knowm.xchange.itbit.v1;

import java.io.IOException;
import java.util.Date;
import java.util.List;
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
import org.knowm.xchange.itbit.v1.dto.ItBitException;
import org.knowm.xchange.itbit.v1.dto.ItBitFundingHistoryResponse;
import org.knowm.xchange.itbit.v1.dto.account.ItBitAccountInfoReturn;
import org.knowm.xchange.itbit.v1.dto.account.ItBitDepositRequest;
import org.knowm.xchange.itbit.v1.dto.account.ItBitDepositResponse;
import org.knowm.xchange.itbit.v1.dto.account.ItBitWithdrawalRequest;
import org.knowm.xchange.itbit.v1.dto.account.ItBitWithdrawalResponse;
import org.knowm.xchange.itbit.v1.dto.marketdata.ItBitTicker;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitOrder;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitPlaceOrderRequest;
import org.knowm.xchange.itbit.v1.dto.trade.ItBitTradeHistory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface ItBitAuthenticated {

  @GET
  @Path("/markets/{ident}{currency}/ticker")
  ItBitTicker getTicker(
      @PathParam("ident") String tradeableIdentifier, @PathParam("currency") String currency)
      throws IOException, ItBitException;

  @GET
  @Path("wallets")
  ItBitAccountInfoReturn[] getInfo(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("userId") String userId)
      throws IOException, ItBitException;

  @GET
  @Path("wallets/{walletId}")
  ItBitAccountInfoReturn getWallet(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId)
      throws IOException, ItBitException;

  @GET
  @Path("wallets/{walletId}/orders")
  ItBitOrder[] getOrders(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @QueryParam("instrument") String instrument,
      @QueryParam("page") String page,
      @QueryParam("perPage") String perPage,
      @QueryParam("status") String status,
      @PathParam("walletId") String walletId)
      throws IOException, ItBitException;

  @GET
  @Path("wallets/{walletId}/orders/{orderId}")
  ItBitOrder getOrder(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      @PathParam("orderId") String orderId)
      throws IOException, ItBitException;

  @POST
  @Path("wallets/{walletId}/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitOrder postOrder(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      ItBitPlaceOrderRequest request)
      throws IOException, ItBitException;

  /** Returns empty body, return object is always null */
  @DELETE
  @Path("wallets/{walletId}/orders/{orderId}")
  Object cancelOrder(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      @PathParam("orderId") String orderId)
      throws IOException, ItBitException;

  @POST
  @Path("wallets/{walletId}/cryptocurrency_withdrawals")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitWithdrawalResponse requestWithdrawal(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      ItBitWithdrawalRequest request)
      throws IOException, ItBitException;

  @POST
  @Path("wallets/{walletId}/cryptocurrency_deposits")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitDepositResponse requestDeposit(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      ItBitDepositRequest request)
      throws IOException, ItBitException;

  @GET
  @Path("wallets/{walletId}/trades")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitTradeHistory getUserTradeHistory(
      @HeaderParam("Authorization") ParamsDigest signer,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> valueFactory,
      @PathParam("walletId") String walletId,
      @QueryParam("lastExecutionId") String lastExecutionId,
      @QueryParam("page") Integer page,
      @QueryParam("perPage") Integer perPage,
      @QueryParam("rangeStart") Date rangeStart,
      @QueryParam("rangeEnd") Date rangeEnd)
      throws IOException, ItBitException;

  @GET
  @Path("wallets")
  @Consumes(MediaType.APPLICATION_JSON)
  List wallets(
      @HeaderParam("Authorization") ParamsDigest signatureCreator,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @QueryParam("userId") String userId,
      @QueryParam("page") int page,
      @QueryParam("perPage") int perPage);

  @GET
  @Path("wallets/{walletId}/funding_history")
  @Consumes(MediaType.APPLICATION_JSON)
  ItBitFundingHistoryResponse fundingHistory(
      @HeaderParam("Authorization") ParamsDigest signatureCreator,
      @HeaderParam("X-Auth-Nonce") SynchronizedValueFactory<Long> nonceFactory,
      @HeaderParam("X-Auth-Timestamp") long timestamp,
      @PathParam("walletId") String walletId,
      @QueryParam("page") int page,
      @QueryParam("perPage") int perPage);
}
