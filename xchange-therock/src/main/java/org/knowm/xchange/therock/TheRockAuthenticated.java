package org.knowm.xchange.therock;

import java.io.IOException;
import java.util.Date;

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

import org.knowm.xchange.therock.dto.TheRockException;
import org.knowm.xchange.therock.dto.account.TheRockBalance;
import org.knowm.xchange.therock.dto.account.TheRockBalances;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawal;
import org.knowm.xchange.therock.dto.account.TheRockWithdrawalResponse;
import org.knowm.xchange.therock.dto.trade.TheRockOrder;
import org.knowm.xchange.therock.dto.trade.TheRockOrders;
import org.knowm.xchange.therock.dto.trade.TheRockTransactions;
import org.knowm.xchange.therock.dto.trade.TheRockUserTrades;
import org.knowm.xchange.therock.service.TheRockDigest;

import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface TheRockAuthenticated {

  String X_TRT_SIGN = "X-TRT-SIGN";
  String X_TRT_KEY = "X-TRT-KEY";
  String X_TRT_NONCE = "X-TRT-NONCE";

  //account

  @GET
  @Path("balances/{currency}")
  TheRockBalance balance(@HeaderParam(X_TRT_KEY) String publicKey, @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory,
      @PathParam("currency") String currency) throws TheRockException, IOException;

  @GET
  @Path("balances")
  TheRockBalances balances(@HeaderParam(X_TRT_KEY) String publicKey, @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory) throws TheRockException, IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("atms/withdraw")
  TheRockWithdrawalResponse withdraw(@HeaderParam(X_TRT_KEY) String publicKey, @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory, TheRockWithdrawal withdrawal) throws TheRockException, IOException;

  //trading

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("funds/{fund_id}/orders")
  TheRockOrder placeOrder(@PathParam("fund_id") TheRock.Pair currencyPair, @HeaderParam(X_TRT_KEY) String publicKey,
      @HeaderParam(X_TRT_SIGN) TheRockDigest signer, @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory,
      TheRockOrder order) throws TheRockException, IOException;

  @DELETE
  @Path("funds/{fund_id}/orders/{id}")
  TheRockOrder cancelOrder(@PathParam("fund_id") TheRock.Pair currencyPair, @PathParam("id") Long orderId, @HeaderParam(X_TRT_KEY) String publicKey,
      @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory) throws TheRockException, IOException;

  @GET
  @Path("funds/{fund_id}/orders")
  TheRockOrders orders(@PathParam("fund_id") TheRock.Pair currencyPair, @HeaderParam(X_TRT_KEY) String publicKey,
      @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory) throws TheRockException, IOException;

  @GET
  @Path("funds/{fund_id}/orders/{id}")
  TheRockOrder showOrder(@PathParam("fund_id") TheRock.Pair currencyPair, @PathParam("id") Long orderId, @HeaderParam(X_TRT_KEY) String publicKey,
      @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory) throws TheRockException, IOException;

  @GET
  @Path("funds/{fund_id}/trades")
  TheRockUserTrades trades(@PathParam("fund_id") TheRock.Pair currencyPair, @HeaderParam(X_TRT_KEY) String publicKey,
      @HeaderParam(X_TRT_SIGN) TheRockDigest signer, @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory,
      @QueryParam("trade_id") Long sinceTradeId, @QueryParam("after") Date after, @QueryParam("before") Date before,
      @QueryParam("per_page") int perPage, @QueryParam("page") int page) throws TheRockException, IOException;

  @GET
  @Path("transactions")
  TheRockTransactions transactions(@HeaderParam(X_TRT_KEY) String publicKey, @HeaderParam(X_TRT_SIGN) TheRockDigest signer,
      @HeaderParam(X_TRT_NONCE) SynchronizedValueFactory<Long> nonceFactory, @QueryParam("type") String type, @QueryParam("after") Date after,
      @QueryParam("before") Date before, @QueryParam("currency") String currency, @QueryParam("page") Integer page) throws TheRockException, IOException;
}
