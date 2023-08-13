package org.knowm.xchange.krakenfutures;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import org.knowm.xchange.krakenfutures.dto.account.KrakenFuturesAccounts;
import org.knowm.xchange.krakenfutures.dto.marketData.KrakenFuturesOrder;
import org.knowm.xchange.krakenfutures.dto.trade.*;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author Jean-Christophe Laruelle */
@Path("/api/v3")
@Produces(MediaType.APPLICATION_JSON)
public interface KrakenFuturesAuthenticated extends KrakenFutures {

  @GET
  @Path("accounts")
  KrakenFuturesAccounts accounts(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("sendorder")
  KrakenFuturesOrder sendOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("orderType") String orderType,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") String side,
      @QueryParam("size") BigDecimal size,
      @QueryParam("limitPrice") BigDecimal limitPrice,
      @QueryParam("cliOrdId") String cliOrdId,
      @QueryParam("reduceOnly") boolean reduceOnly,
      @QueryParam("stopPrice") BigDecimal stopPrice,
      @QueryParam("triggerSignal") String triggerSignal)
      throws IOException;

  @POST
  @Path("batchorder")
  BatchOrderResult batchOrder(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("json") BatchOrder orderCommands)
      throws IOException;

    @POST
    @Path("cancelorder")
    KrakenFuturesCancel cancelOrder(
        @HeaderParam("APIKey") String apiKey,
        @HeaderParam("Authent") ParamsDigest signer,
        @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
        @QueryParam("order_id") String order_id)
        throws IOException;

  @POST
  @Path("cancelallorders")
  KrakenFuturesCancelAllOrders cancelAllOrdersByInstrument(
          @HeaderParam("APIKey") String apiKey,
          @HeaderParam("Authent") ParamsDigest signer,
          @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
          @QueryParam("symbol") String symbol)
          throws IOException;

  @POST
  @Path("cancelallordersafter")
  KrakenFuturesCancelAllOrdersAfter cancelAllOrdersAfter(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("timeout") long timeoutSeconds)
      throws IOException;

  @GET
  @Path("openorders")
  KrakenFuturesOpenOrders openOrders(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("orders/status")
  @Produces(MediaType.APPLICATION_FORM_URLENCODED)
  KrakenFuturesOrdersStatusesResponse getOrdersStatuses(
          @HeaderParam("APIKey") String apiKey,
          @HeaderParam("Authent") ParamsDigest signer,
          @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
          KrakenFuturesOrderStatusRequest orderIds)
          throws IOException;

  @POST
  @Path("editorder")
  KrakenFuturesEditOrderResponse changeOrder(
          @HeaderParam("APIKey") String apiKey,
          @HeaderParam("Authent") ParamsDigest signer,
          @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
          @QueryParam("cliOrdId") String cliOrdId,
          @QueryParam("limitPrice") BigDecimal limitPrice,
          @QueryParam("orderId") String orderId,
          @QueryParam("size") BigDecimal size,
          @QueryParam("stopPrice") BigDecimal stopPrice
  )
          throws IOException;

  @GET
  @Path("fills")
  KrakenFuturesFills fills(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce,
      @QueryParam("lastFillTime") String lastFillTime)
      throws IOException;

  @GET
  @Path("openpositions")
  KrakenFuturesOpenPositions openPositions(
      @HeaderParam("APIKey") String apiKey,
      @HeaderParam("Authent") ParamsDigest signer,
      @HeaderParam("Nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;
}