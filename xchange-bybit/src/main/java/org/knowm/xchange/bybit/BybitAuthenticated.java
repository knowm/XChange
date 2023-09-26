package org.knowm.xchange.bybit;

import static org.knowm.xchange.bybit.service.BybitDigest.X_BAPI_API_KEY;
import static org.knowm.xchange.bybit.service.BybitDigest.X_BAPI_SIGN;
import static org.knowm.xchange.bybit.service.BybitDigest.X_BAPI_TIMESTAMP;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitTransfersResponse;
import org.knowm.xchange.bybit.dto.account.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.bybit.dto.trade.BybitTradeHistoryResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.bybit.service.BybitException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/v5")
@Produces(MediaType.APPLICATION_JSON)
public interface BybitAuthenticated {

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/account/wallet-balance">API</a> */
  @GET
  @Path("/account/wallet-balance")
  BybitResult<BybitWalletBalance> getWalletBalance(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("accountType") String accountType)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/asset/all-balance">API</a> */
  @GET
  @Path("/asset/transfer/query-account-coins-balance")
  BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("memberId") String memberId,
      @QueryParam("accountType") String accountType,
      @QueryParam("coin") String coin,
      @QueryParam("withBonus") Integer withBonus
  ) throws IOException, BybitException;

  @GET
  @Path("/asset/transfer/query-account-coin-balance")
  BybitResult<BybitAllCoinsBalance> getSingleCoinBalance(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("memberId") String memberId,
      @QueryParam("toMemberId") String toMemberId,
      @QueryParam("accountType") String accountType, //required
      @QueryParam("toAccountType") String toAccountType,
      @QueryParam("coin") String coin, //required
      @QueryParam("withBonus") Integer withBonus,
      @QueryParam("withTransferSafeAmount") Integer withTransferSafeAmount,
      @QueryParam("withLtvTransferSafeAmount") Integer withLtvTransferSafeAmount
  ) throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/account/fee-rate">API</a> */
  @GET
  @Path("/account/fee-rate")
  BybitResult<BybitFeeRates> getFeeRates(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/order/open-order">API</a> */
  @GET
  @Path("/order/realtime")
  BybitResult<BybitOrderDetails<BybitOrderDetail>> getOpenOrders(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("category") String category,
      @QueryParam("orderId") String orderId)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/order/create-order">API</a> */
  @POST
  @Path("/order/create")
  BybitResult<BybitOrderResponse> placeOrder(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @FormParam("category") String category,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("orderType") String orderType,
      @FormParam("qty") BigDecimal qty)
      throws IOException, BybitException;

  @GET
  @Path("/execution/list")
  BybitResult<BybitTradeHistoryResponse> getBybitTradeHistory(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol,
      @QueryParam("orderId") String orderId,
      @QueryParam("orderLinkId") String userReferenceId,
      @QueryParam("baseCoin") String baseCoin, // Base coin. Unified account - inverse and Classic account do not support this param
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("execType") String execType,
      @QueryParam("limit") Integer limit,
      @QueryParam("cursor") String cursor
  ) throws IOException, BybitException;

  @GET
  @Path("/asset/transfer/query-inter-transfer-list")
  BybitResult<BybitTransfersResponse> getInternalTransferRecords(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("transferId") String transferId,
      @QueryParam("coin") String coin,
      @QueryParam("status") String status,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("cursor") String cursor
  ) throws IOException, BybitException;

  @GET
  @Path("/asset/transfer/query-universal-transfer-list")
  BybitResult<BybitTransfersResponse> getUniversalTransferRecords(
      @HeaderParam(X_BAPI_API_KEY) String apiKey,
      @HeaderParam(X_BAPI_SIGN) ParamsDigest signature,
      @HeaderParam(X_BAPI_TIMESTAMP) SynchronizedValueFactory<Long> timestamp,
      @QueryParam("transferId") String transferId,
      @QueryParam("coin") String coin,
      @QueryParam("status") String status,
      @QueryParam("startTime") Long startTime,
      @QueryParam("endTime") Long endTime,
      @QueryParam("limit") Integer limit,
      @QueryParam("cursor") String cursor
  ) throws IOException, BybitException;
}
