package org.knowm.xchange.okex.v5;

import java.io.IOException;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.account.OkexWalletBalance;
import org.knowm.xchange.okex.v5.dto.marketdata.OkexCurrency;
import org.knowm.xchange.okex.v5.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexCancelOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderResponse;
import org.knowm.xchange.okex.v5.dto.trade.OkexPendingOrder;
import si.mazi.rescu.ParamsDigest;

@Path("/api/v5")
@Produces(MediaType.APPLICATION_JSON)
public interface OkexAuthenticated extends Okex {
  String balancePath = "/account/balance"; // Stated as 10 req/2 sec
  String currenciesPath = "/asset/currencies"; // Stated as 6 req/sec
  String pendingOrdersPath = "/trade/orders-pending"; // Stated as 20 req/2 sec
  String placeOrderPath = "/trade/order"; // Stated as 60 req/2 sec
  String placeBatchOrderPath = "/trade/batch-orders"; // Stated as 300 req/2 sec
  String cancelOrderPath = "/trade/cancel-order"; // Stated as 60 req/2 sec
  String cancelBatchOrderPath = "trade/cancel-batch-orders"; // Stated as 300 req/2 sec
  String amendOrderPath = "trade/amend-order"; // Stated as 60 req/2 sec
  String amendBatchOrderPath = "trade/amend-batch-orders"; // Stated as 300 req/2 sec

  // To avoid 429s, actual req/second may need to be lowered!
  Map<String, List<Integer>> privatePathRateLimits =
      new HashMap<String, List<Integer>>() {
        {
          put(balancePath, Arrays.asList(5, 1));
          put(currenciesPath, Arrays.asList(6, 1));
          put(pendingOrdersPath, Arrays.asList(20, 2));
          put(placeOrderPath, Arrays.asList(60, 2));
          put(placeBatchOrderPath, Arrays.asList(300, 2));
          put(cancelOrderPath, Arrays.asList(60, 2));
          put(cancelBatchOrderPath, Arrays.asList(300, 2));
          put(amendOrderPath, Arrays.asList(60, 2));
          put(amendBatchOrderPath, Arrays.asList(300, 2));
        }
      };

  @GET
  @Path(balancePath)
  OkexResponse<List<OkexWalletBalance>> getWalletBalances(
      @QueryParam("ccy") List<Currency> currencies,
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase)
      throws IOException, OkexException;

  @GET
  @Path(currenciesPath)
  OkexResponse<List<OkexCurrency>> getCurrencies(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase)
      throws OkexException, IOException;

  @GET
  @Path(pendingOrdersPath)
  OkexResponse<List<OkexPendingOrder>> getPendingOrders(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      @QueryParam("instType") String instrumentType,
      @QueryParam("uly") String underlying,
      @QueryParam("instId") String instrumentId,
      @QueryParam("ordType") String orderType,
      @QueryParam("state") String state,
      @QueryParam("after") String after,
      @QueryParam("before") String before,
      @QueryParam("limit") String limit)
      throws OkexException, IOException;

  @POST
  @Path(placeOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> placeOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      OkexOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(placeBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> placeBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      List<OkexOrderRequest> requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(cancelOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> cancelOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      OkexCancelOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(cancelBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> cancelBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      List<OkexCancelOrderRequest> requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(amendOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> amendOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      OkexAmendOrderRequest requestPayload)
      throws OkexException, IOException;

  @POST
  @Path(amendBatchOrderPath)
  @Consumes(MediaType.APPLICATION_JSON)
  OkexResponse<List<OkexOrderResponse>> amendBatchOrder(
      @HeaderParam("OK-ACCESS-KEY") String apiKey,
      @HeaderParam("OK-ACCESS-SIGN") ParamsDigest signature,
      @HeaderParam("OK-ACCESS-TIMESTAMP") String timestamp,
      @HeaderParam("OK-ACCESS-PASSPHRASE") String passphrase,
      List<OkexAmendOrderRequest> requestPayload)
      throws OkexException, IOException;
}
