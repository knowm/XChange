package org.knowm.xchange.yobit;

import java.io.IOException;
import java.math.BigDecimal;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.yobit.dto.BaseYoBitResponse;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.dto.marketdata.YoBitOrderBooksReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTickersReturn;
import org.knowm.xchange.yobit.dto.marketdata.YoBitTrades;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface YoBit {
  @GET
  @Path("api/3/info")
  YoBitInfo getProducts() throws IOException;

  @GET
  @Path("api/3/depth/{currencyPairs}")
  YoBitOrderBooksReturn getOrderBooks(
      @PathParam("currencyPairs") String currencyPairs, @QueryParam("limit") long limit)
      throws IOException;

  @GET
  @Path("api/3/trades/{currencyPairs}")
  YoBitTrades getTrades(@PathParam("currencyPairs") String currencyPairsCurrency)
      throws IOException;

  @GET
  @Path("api/3/ticker/{currencyPairs}")
  YoBitTickersReturn getTickers(@PathParam("currencyPairs") String currencyPairs)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse getInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse withdrawCoinsToAddress(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("coinName") String coinName,
      @FormParam("amount") BigDecimal amount,
      @FormParam("address") String address)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse getDepositAddress(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("coinName") String coinName,
      @FormParam("need_new") Boolean needNew)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse tradeHistory(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("form") Long offset,
      @FormParam("count") Integer count,
      @FormParam("from_id") Long fromTransactionId,
      @FormParam("end_id") Long endTransactionId,
      @FormParam("order") String order,
      @FormParam("since") Long sinceTime,
      @FormParam("end") Long endTime,
      @FormParam("pair") String currencyPair)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse activeOrders(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("pair") String market)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse orderInfo(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") Long orderId)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse cancelOrder(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("order_id") Long orderId)
      throws IOException;

  @POST
  @Path("/tapi")
  BaseYoBitResponse trade(
      @HeaderParam("Key") String apiKey,
      @HeaderParam("Sign") YoBitDigest signatureCreator,
      @FormParam("method") String method,
      @FormParam("nonce") SynchronizedValueFactory<Long> nonce,
      @FormParam("pair") String market,
      @FormParam("type") String type,
      @FormParam("rate") BigDecimal price,
      @FormParam("amount") BigDecimal amount)
      throws IOException;
}
