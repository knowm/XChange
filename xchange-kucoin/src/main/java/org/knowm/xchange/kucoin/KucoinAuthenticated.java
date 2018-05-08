package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.KucoinOrderType;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.KucoinSimpleResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinCoinBalances;
import org.knowm.xchange.kucoin.dto.account.KucoinDepositAddress;
import org.knowm.xchange.kucoin.dto.account.KucoinWalletRecords;
import org.knowm.xchange.kucoin.dto.trading.KucoinActiveOrders;
import org.knowm.xchange.kucoin.dto.trading.KucoinDealtOrdersInfo;
import org.knowm.xchange.kucoin.dto.trading.KucoinOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface KucoinAuthenticated extends Kucoin {

  static final String HEADER_APIKEY = "KC-API-KEY";
  static final String HEADER_NONCE = "KC-API-NONCE";
  static final String HEADER_SIGNATURE = "KC-API-SIGNATURE";

  @GET
  @Path("account/balances")
  KucoinResponse<KucoinCoinBalances> accountBalances(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("limit") Integer limit, // default 12, max 20
      @QueryParam("page") Integer page // default 1
      ) throws IOException, KucoinException;

  /** Retrieve funding history (deposit and withdrawal history) */
  @GET
  @Path("account/{coin}/wallet/records")
  KucoinResponse<KucoinWalletRecords> walletRecords(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @PathParam("coin") String coin,
      @QueryParam("type") String type,
      @QueryParam("status") String status,
      @QueryParam("limit") Integer limit,
      @QueryParam("page") Integer page)
      throws IOException, KucoinException;

  @GET
  @Path("account/{coin}/wallet/address")
  KucoinResponse<KucoinDepositAddress> walletAddress(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @PathParam("coin") String coin)
      throws IOException, KucoinException;

  @POST
  @Path("account/{coin}/withdraw/apply")
  KucoinSimpleResponse<Object> withdrawalApply(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @PathParam("coin") String coin,
      @QueryParam("amount") BigDecimal amount,
      @QueryParam("address") String address)
      throws IOException, KucoinException;

  /** Lists all active orders for a currency pair. */
  @GET
  @Path("order/active")
  KucoinResponse<KucoinActiveOrders> orderActive(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("type") KucoinOrderType type)
      throws IOException, KucoinException;

  /** Returns the trade history. */
  @GET
  @Path("order/dealt")
  KucoinResponse<KucoinDealtOrdersInfo> orderDealt(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("type") KucoinOrderType type,
      @QueryParam("limit") Integer limit,
      @QueryParam("page") Integer page,
      @QueryParam("since") Long since,
      @QueryParam("before") Long before)
      throws IOException, KucoinException;

  /** Places a limit order. */
  @POST
  @Path("order")
  KucoinResponse<KucoinOrder> order(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("type") KucoinOrderType type,
      @QueryParam("price") BigDecimal price,
      @QueryParam("amount") BigDecimal amount)
      throws IOException, KucoinException;

  /** Cancels an order. */
  @POST
  @Path("cancel-order")
  KucoinResponse<KucoinOrder> cancelOrder(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("orderOid") String orderOid,
      @QueryParam("type") KucoinOrderType type)
      throws IOException, KucoinException;
}
