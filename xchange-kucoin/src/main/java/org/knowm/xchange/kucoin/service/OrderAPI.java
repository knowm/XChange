/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

import java.io.IOException;
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
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCreateResponse;
import org.knowm.xchange.kucoin.dto.response.OrderResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** Based on code by chenshiwei on 2019/1/10. */
@Path("/api/v1/orders")
@Produces(MediaType.APPLICATION_JSON)
public interface OrderAPI {

  /**
   * Place a new order.
   *
   * <p>You can place two types of orders: limit and market. Orders can only be placed if your
   * account has sufficient funds. Once an order is placed, your account funds will be put on hold
   * for the duration of the order. How much and which funds are put on hold depends on the order
   * type and parameters specified.
   *
   * <p>The maximum matching orders for a single trading pair in one account is 50 (stop limit order
   * included).
   *
   * @param opsRequest Order creation request.
   * @return A response containing the order id.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  KucoinResponse<OrderCreateResponse> createOrder(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      OrderCreateApiRequest opsRequest)
      throws IOException;

  /**
   * Cancel an order
   *
   * <p>Cancel a previously placed order.
   *
   * @param orderId The order id.
   * @return A response containing the id of the cancelled order.
   */
  @DELETE
  @Path("/{orderId}")
  KucoinResponse<OrderCancelResponse> cancelOrder(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @PathParam("orderId") String orderId)
      throws IOException;

  /**
   * With best effort, cancel all open orders. The response is a list of ids of the canceled orders.
   *
   * @param symbol The symbol whose orders should be cancelled.
   * @return A response containing the ids of all open orders.
   */
  @DELETE
  KucoinResponse<OrderCancelResponse> cancelOrders(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("symbol") String symbol)
      throws IOException;

  /**
   * Get a single order by order id.
   *
   * @param orderId The order id.
   * @return The requested order.
   */
  @GET
  @Path("/{orderId}")
  KucoinResponse<OrderResponse> getOrder(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @PathParam("orderId") String orderId)
      throws IOException;

  /**
   * List your current orders.
   *
   * @param symbol [optional] Only list orders for a specific symbol.
   * @param side [optional] buy or sell
   * @param type [optional] limit, market, limit_stop or market_stop
   * @param status [optional] active or done, done as default, Only list orders for a specific
   *     status .
   * @param startAt [optional] Start time. unix timestamp calculated in milliseconds, the creation
   *     time queried shall posterior to the start time.
   * @param endAt [optional] End time. unix timestamp calculated in milliseconds, the creation time
   *     queried shall prior to the end time.
   * @param pageSize The page size.
   * @param currentPage The page to select.
   * @return A page of orders.
   */
  @GET
  KucoinResponse<Pagination<OrderResponse>> queryOrders(
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase,
      @QueryParam("symbol") String symbol,
      @QueryParam("side") String side,
      @QueryParam("type") String type,
      @QueryParam("status") String status,
      @QueryParam("startAt") Long startAt,
      @QueryParam("endAt") Long endAt,
      @QueryParam("pageSize") Integer pageSize,
      @QueryParam("currentPage") Integer currentPage)
      throws IOException;
}