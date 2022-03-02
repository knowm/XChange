package org.knowm.xchange.globitex;

import java.io.IOException;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.globitex.dto.account.GlobitexAccounts;
import org.knowm.xchange.globitex.dto.trade.GlobitexActiveOrders;
import org.knowm.xchange.globitex.dto.trade.GlobitexExecutionReport;
import org.knowm.xchange.globitex.dto.trade.GlobitexUserTrades;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/api/1/")
@Produces(MediaType.APPLICATION_JSON)
public interface GlobitexAuthenticated extends Globitex {

  @GET
  @Path("payment/accounts")
  GlobitexAccounts getAccounts(
      @HeaderParam("X-API-Key") String apiKey,
      @HeaderParam("X-Nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("trading/trades")
  GlobitexUserTrades getTradeHistory(
      @HeaderParam("X-API-Key") String apiKey,
      @HeaderParam("X-Nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("by") String sortBy,
      @QueryParam("startIndex") int startIndex,
      @QueryParam("maxResults") int limit,
      @QueryParam("symbols") String currencies,
      @QueryParam("account") String account)
      throws IOException;

  @GET
  @Path("trading/orders/active")
  GlobitexActiveOrders getActiveOrders(
      @HeaderParam("X-API-Key") String apiKey,
      @HeaderParam("X-Nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-Signature") ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("clientOrderId") String clientOrderId,
      @QueryParam("account") String account)
      throws IOException;

  @POST
  @Path("trading/new_order")
  GlobitexExecutionReport placeNewOrder(
      @HeaderParam("X-API-Key") String apiKey,
      @HeaderParam("X-Nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-Signature") ParamsDigest signature,
      @FormParam("account") String account,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("price") String price,
      @FormParam("quantity") String quantity)
      throws IOException;

  @POST
  @Path("trading/cancel_order")
  GlobitexExecutionReport cancelOrder(
      @HeaderParam("X-API-Key") String apiKey,
      @HeaderParam("X-Nonce") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-Signature") ParamsDigest signature,
      @FormParam("clientOrderId") String clientOrderId)
      throws IOException;
}
