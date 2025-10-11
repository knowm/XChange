package org.knowm.xchange.dase;

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
import java.io.IOException;
import org.knowm.xchange.dase.dto.account.ApiGetAccountTxnsOutput;
import org.knowm.xchange.dase.dto.account.DaseBalancesResponse;
import org.knowm.xchange.dase.dto.account.DaseSingleBalance;
import org.knowm.xchange.dase.dto.trade.DaseBatchCancelOrdersRequest;
import org.knowm.xchange.dase.dto.trade.DaseBatchGetOrdersRequest;
import org.knowm.xchange.dase.dto.trade.DaseBatchGetOrdersResponse;
import org.knowm.xchange.dase.dto.trade.DaseCancelAllOrdersQuery;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dase.dto.trade.DaseOrdersListResponse;
import org.knowm.xchange.dase.dto.trade.DasePlaceOrderInput;
import org.knowm.xchange.dase.dto.trade.DasePlaceOrderResponse;
import org.knowm.xchange.dase.dto.user.DaseUserProfile;
import si.mazi.rescu.ParamsDigest;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface DaseAuthenticated {

  @GET
  @Path("/users/me")
  DaseUserProfile getUserProfile(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp)
      throws IOException;

  @GET
  @Path("/accounts/transactions")
  ApiGetAccountTxnsOutput getAccountTransactions(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      @QueryParam("limit") Integer limit,
      @QueryParam("before") String before)
      throws IOException;

  // Orders
  @GET
  @Path("/orders")
  DaseOrdersListResponse getOrders(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      @QueryParam("market") String market,
      @QueryParam("status") String status,
      @QueryParam("limit") Integer limit,
      @QueryParam("before") String before)
      throws IOException;

  @POST
  @Path("/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  DasePlaceOrderResponse placeOrder(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      DasePlaceOrderInput body)
      throws IOException;

  @GET
  @Path("/orders/{order_id}")
  DaseOrder getOrder(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      @PathParam("order_id") String orderId)
      throws IOException;

  @DELETE
  @Path("/orders/{order_id}")
  Void cancelOrder(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      @PathParam("order_id") String orderId)
      throws IOException;

  @DELETE
  @Path("/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  Void batchCancelOrders(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      DaseBatchCancelOrdersRequest body)
      throws IOException;

  @DELETE
  @Path("/orders/")
  @Consumes(MediaType.APPLICATION_JSON)
  Void cancelAllOrders(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      DaseCancelAllOrdersQuery body)
      throws IOException;

  @POST
  @Path("/orders/search")
  @Consumes(MediaType.APPLICATION_JSON)
  DaseBatchGetOrdersResponse batchGetOrders(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp,
      DaseBatchGetOrdersRequest body)
      throws IOException;

  @GET
  @Path("/balances")
  DaseBalancesResponse getBalances(
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp)
      throws IOException;

  @GET
  @Path("/balances/{currency}")
  DaseSingleBalance getBalance(
      @PathParam("currency") String currency,
      @HeaderParam("ex-api-key") String apiKey,
      @HeaderParam("ex-api-sign") ParamsDigest signer,
      @HeaderParam("ex-api-timestamp") String timestamp)
      throws IOException;
}
