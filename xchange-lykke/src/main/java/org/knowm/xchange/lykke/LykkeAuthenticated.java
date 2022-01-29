package org.knowm.xchange.lykke;

import java.io.IOException;
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
