package org.knowm.xchange.lgo;

import java.io.IOException;
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
import org.knowm.xchange.lgo.dto.LgoException;
import org.knowm.xchange.lgo.dto.WithCursor;
import org.knowm.xchange.lgo.dto.currency.LgoCurrencies;
import org.knowm.xchange.lgo.dto.marketdata.LgoOrderbook;
import org.knowm.xchange.lgo.dto.marketdata.LgoPriceHistory;
import org.knowm.xchange.lgo.dto.order.LgoEncryptedOrder;
import org.knowm.xchange.lgo.dto.order.LgoPlaceOrderResponse;
import org.knowm.xchange.lgo.dto.order.LgoUnencryptedOrder;
import org.knowm.xchange.lgo.dto.product.LgoProducts;
import org.knowm.xchange.lgo.dto.trade.LgoUserTrades;
import si.mazi.rescu.ParamsDigest;

@Produces(MediaType.APPLICATION_JSON)
@Path("/v1")
public interface Lgo {

  String X_LGO_DATE = "X-LGO-DATE";
  String AUTHORIZATION = "Authorization";
  String PRODUCT_ID = "product_id";
  String MAX_RESULTS = "max_results";
  String PAGE = "page";
  String SORT = "sort";
  String ORDER_ID = "order_id";
  String GRANULARITY = "granularity";
  String END = "end";
  String START = "start";

  @GET
  @Path("/live/products")
  LgoProducts getProducts(
      @HeaderParam(X_LGO_DATE) long timestamp, @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws IOException, LgoException;

  @GET
  @Path("/live/currencies")
  LgoCurrencies getCurrencies(
      @HeaderParam(X_LGO_DATE) long timestamp, @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws IOException, LgoException;

  @GET
  @Path("/history/trades")
  WithCursor<LgoUserTrades> getLastTrades(
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature,
      @QueryParam(PRODUCT_ID) String productId,
      @QueryParam(MAX_RESULTS) int maxResults,
      @QueryParam(PAGE) String page,
      @QueryParam(SORT) String sort)
      throws IOException, LgoException;

  @GET
  @Path("/live/products/{product_id}/book")
  LgoOrderbook getOrderBook(
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature,
      @PathParam(PRODUCT_ID) String productId)
      throws IOException, LgoException;

  @POST
  @Path("/live/orders/encrypted")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  LgoPlaceOrderResponse placeEncryptedOrder(
      LgoEncryptedOrder placeOrder,
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws IOException, LgoException;

  @POST
  @Path("/live/orders")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  LgoPlaceOrderResponse placeUnencryptedOrder(
      LgoUnencryptedOrder placeOrder,
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature)
      throws IOException, LgoException;

  @DELETE
  @Path("/live/orders/{order_id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  LgoPlaceOrderResponse placeUnencryptedCancelOrder(
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature,
      @PathParam(ORDER_ID) String orderId)
      throws IOException, LgoException;

  @GET
  @Path("/history/products/{product_id}/candles")
  LgoPriceHistory getPriceHistory(
      @HeaderParam(X_LGO_DATE) long timestamp,
      @HeaderParam(AUTHORIZATION) ParamsDigest signature,
      @PathParam(PRODUCT_ID) String productId,
      @QueryParam(START) String startTime,
      @QueryParam(END) String endTime,
      @QueryParam(GRANULARITY) int granularity)
      throws IOException, LgoException;
}
