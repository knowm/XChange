/** Copyright 2019 Mek Global Limited. */
package org.knowm.xchange.kucoin.service;

import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.kucoin.dto.response.KucoinResponse;
import org.knowm.xchange.kucoin.dto.response.OrderBookResponse;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

/** Based on code by chenshiwei on 2019/1/22. */
@Produces(MediaType.APPLICATION_JSON)
@Path("api")
public interface OrderBookAPI {

  /**
   * Get a list of open orders for a symbol. Level-2 order book includes all bids and asks
   * (aggregated by price), this level return only one size for each active price (as if there was
   * only a single order for that size at the level). This API will return a part of Order Book
   * within 20 depth for each side (ask or bid). It is provided to use if you don't need a depth of
   * 100 (valid in some cases). To maintain up-to-date Order Book in real time, please use it with
   * Websocket Feed.
   *
   * @param symbol The symbol whose order book should be fetched.
   * @return The aggregated part order book.
   */
  @GET
  @Path("v1/market/orderbook/level2_20")
  KucoinResponse<OrderBookResponse> getPartOrderBookShallowAggregated(
      @QueryParam("symbol") String symbol) throws IOException;

  /**
   * Get a list of open orders for a symbol. Level-2 order book includes all bids and asks
   * (aggregated by price), this level return only one size for each active price (as if there was
   * only a single order for that size at the level). This API will return a part of Order Book
   * within 100 depth for each side(ask or bid). It is recommended to use in most cases, it is the
   * fastest Order Book API, and reduces traffic usage. To maintain up-to-date Order Book in real
   * time, please use it with Websocket Feed.
   *
   * @param symbol The symbol whose order book should be fetched.
   * @return The aggregated part order book.
   */
  @GET
  @Path("v1/market/orderbook/level2_100")
  KucoinResponse<OrderBookResponse> getPartOrderBookAggregated(@QueryParam("symbol") String symbol)
      throws IOException;

  /**
   * Get a list of open orders for a symbol. Level-2 order book includes all bids and asks
   * (aggregated by price), this level return only one size for each active price (as if there was
   * only a single order for that size at the level). This API will return data with full depth. It
   * is generally used by professional traders because it uses more server resources and traffic,
   * and we have strict access frequency control. To maintain up-to-date Order Book in real time,
   * please use it with Websocket Feed.
   *
   * @param symbol The symbol whose order book should be fetched.
   * @return The aggregated full order book.
   */
  @GET
  @Path("v3/market/orderbook/level2")
  KucoinResponse<OrderBookResponse> getFullOrderBookAggregated(
      @QueryParam("symbol") String symbol,
      @HeaderParam(APIConstants.API_HEADER_KEY) String apiKey,
      @HeaderParam(APIConstants.API_HEADER_SIGN) ParamsDigest signature,
      @HeaderParam(APIConstants.API_HEADER_TIMESTAMP) SynchronizedValueFactory<Long> nonce,
      @HeaderParam(APIConstants.API_HEADER_PASSPHRASE) String apiPassphrase)
      throws IOException;

  /**
   * Get a list of open orders for a symbol. Level-3 order book includes all bids and asks
   * (non-aggregated, each item in Level-3 means a single order). Level 3 is non-aggregated and
   * returns the entire order book. This API is generally used by professional traders because it
   * uses more server resources and traffic, and we have strict access frequency control. To
   * Maintain up-to-date Order Book in real time, please use it with Websocket Feed.
   *
   * @param symbol The symbol whose order book should be fetched.
   * @return The full atomic order book.
   */
  @GET
  @Path("v2/market/orderbook/level3")
  KucoinResponse<OrderBookResponse> getFullOrderBookAtomic(@QueryParam("symbol") String symbol)
      throws IOException;
}
