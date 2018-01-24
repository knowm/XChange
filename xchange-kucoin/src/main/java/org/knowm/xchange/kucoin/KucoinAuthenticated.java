package org.knowm.xchange.kucoin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.knowm.xchange.kucoin.dto.KucoinOrderType;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.account.KucoinUserInfoResponse;
import org.knowm.xchange.kucoin.dto.trading.KucoinActiveOrders;
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
  @Path("user/info")
  KucoinUserInfoResponse userInfo(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature
  ) throws IOException;

  /**
   * Lists all active orders for a currency pair.
   */
  @GET
  @Path("order/active")
  KucoinResponse<KucoinActiveOrders> orderActive(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("type") KucoinOrderType type
  ) throws IOException;

  /**
   * Places a limit order.
   */
  @POST
  @Path("order")
  KucoinResponse<KucoinOrder> order(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("type") KucoinOrderType type,
      @QueryParam("price") BigDecimal price,
      @QueryParam("amount") BigDecimal amount
  ) throws IOException;

  /**
   * Cancels an order.
   */
  @POST
  @Path("cancel-order")
  KucoinResponse<KucoinOrder> cancelOrder(
      @HeaderParam(HEADER_APIKEY) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(HEADER_SIGNATURE) ParamsDigest signature,
      @QueryParam("symbol") String symbol,
      @QueryParam("orderOid") String orderOid,
      @QueryParam("type") KucoinOrderType type
  ) throws IOException;

}
