package org.knowm.xchange.lykke;

import java.io.IOException;
import java.util.List;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.lykke.dto.account.LykkeWallet;
import org.knowm.xchange.lykke.dto.trade.LykkeLimitOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeOrder;
import org.knowm.xchange.lykke.dto.trade.LykkeTradeHistory;

@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface LykkeAuthenticated extends Lykke {

  String ApiKey = "api-key";

  @GET
  @Path("history/trades")
  List<LykkeTradeHistory> getTradeHistory(
      @QueryParam("assetPairId") String assetPair,
      @QueryParam("take") int limit,
      @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  @GET
  @Path("history/trades/{tradeId}")
  LykkeTradeHistory getTradeHistoryById(
      @PathParam("tradeId") String tradeId, @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  @GET
  @Path("wallets")
  List<LykkeWallet> getWallets(@HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  // limit : max 500, default: 100
  @GET
  @Path("orders")
  List<LykkeOrder> getLastOrders(
      @QueryParam("status") String status,
      @QueryParam("take") int limit,
      @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  @POST
  @Path("orders/limit")
  String postLimitOrder(LykkeLimitOrder order, @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  @DELETE
  @Path("orders/{id}")
  void cancelOrderById(@PathParam("id") String id, @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;

  @DELETE
  @Path("orders")
  String cancelAllOrders(
      @QueryParam("assetPairId") String assetPairId, @HeaderParam(ApiKey) String apiKey)
      throws IOException, LykkeException;
}