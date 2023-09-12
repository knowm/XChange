package org.knowm.xchange.bybit;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.allcoins.BybitAllCoinsBalance;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRates;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitWalletBalance;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
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
      @QueryParam("api_key") String apiKey,
      @QueryParam("accountType") String accountType,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("sign") ParamsDigest signature)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/asset/all-balance">API</a> */
  @GET
  @Path("/asset/transfer/query-account-coins-balance")
  BybitResult<BybitAllCoinsBalance> getAllCoinsBalance(
      @QueryParam("api_key") String apiKey,
      @QueryParam("accountType") String accountType,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("sign") ParamsDigest signature)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/account/fee-rate">API</a> */
  @GET
  @Path("/account/fee-rate")
  BybitResult<BybitFeeRates> getFeeRates(
      @QueryParam("api_key") String apiKey,
      @QueryParam("category") String category,
      @QueryParam("symbol") String symbol,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("sign") ParamsDigest signature)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/order/open-order">API</a> */
  @GET
  @Path("/order/realtime")
  BybitResult<BybitOrderDetails> getOpenOrders(
      @QueryParam("api_key") String apiKey,
      @QueryParam("category") String category,
      @QueryParam("orderId") String orderId,
      @QueryParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @QueryParam("sign") ParamsDigest signature)
      throws IOException, BybitException;

  /** @apiSpec <a href="https://bybit-exchange.github.io/docs/v5/order/create-order">API</a> */
  @POST
  @Path("/order/create")
  BybitResult<BybitOrderResponse> placeOrder(
      @FormParam("api_key") String apiKey,
      @FormParam("category") String category,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("orderType") String orderType,
      @FormParam("qty") long qty,
      @FormParam("timestamp") SynchronizedValueFactory<Long> timestamp,
      @FormParam("sign") ParamsDigest signature)
      throws IOException, BybitException;
}
