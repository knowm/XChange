package org.knowm.xchange.bx;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bx.dto.account.results.BxBalanceResult;
import org.knowm.xchange.bx.dto.trade.results.BxCancelOrderResult;
import org.knowm.xchange.bx.dto.trade.results.BxCreateOrderResult;
import org.knowm.xchange.bx.dto.trade.results.BxOrdersResult;
import org.knowm.xchange.bx.dto.trade.results.BxTradeHistoryResult;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/")
@Produces(MediaType.APPLICATION_JSON)
public interface BxAuthenticated extends Bx {

  @POST
  @Path("order/")
  BxCreateOrderResult createOrder(
      @FormParam("pairing") String pairId,
      @FormParam("type") String orderType,
      @FormParam("amount") String amount,
      @FormParam("rate") String rate,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("cancel/")
  BxCancelOrderResult cancelOrder(
      @FormParam("pairing") String pairId,
      @FormParam("order_id") String orderId,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("balance/")
  BxBalanceResult getBalance(
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("history/")
  BxTradeHistoryResult getTradeHistory(
      @FormParam("currency") String currency,
      @FormParam("type") String transactionType,
      @FormParam("start_date") String startDate,
      @FormParam("end_date") String endDate,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("getorders/")
  BxOrdersResult getOrders(
      @FormParam("pairing") String pairId,
      @FormParam("type") String orderType,
      @FormParam("key") String apiKey,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("signature") ParamsDigest signature)
      throws IOException;
}
