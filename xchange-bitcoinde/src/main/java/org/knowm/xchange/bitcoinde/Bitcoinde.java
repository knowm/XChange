package org.knowm.xchange.bitcoinde;

import java.io.IOException;
import java.math.BigDecimal;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.dto.account.BitcoindeAccountWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeTradesWrapper;
import org.knowm.xchange.bitcoinde.trade.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOpenOrdersWrapper;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface Bitcoinde {

  // formatter:off

  @DELETE
  @Path("orders/{order_id}/{trading_pair}")
  // https://api.bitcoin.de/v2/orders/:order_id/:trading_pair
  BitcoindeIdResponse deleteOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @PathParam("order_id") String order_id,
      @PathParam("trading_pair") String trading_pair)
      throws BitcoindeException, IOException;

  @POST
  @Path("orders")
  BitcoindeIdResponse createOrder(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
      @FormParam("max_amount") BigDecimal max_amount,
      @FormParam("price") BigDecimal price,
      @FormParam("trading_pair") String trading_pair,
      @FormParam("type") String type)
      throws BitcoindeException, IOException;

  @GET
  @Path("orders/compact")
  BitcoindeOrderbookWrapper getOrderBook(
      @QueryParam("trading_pair") String trading_pair,
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws BitcoindeException, IOException;

  @GET
  @Path("trades/history")
  BitcoindeTradesWrapper getTrades(
      @QueryParam("trading_pair") String trading_pair,
      @QueryParam("since_tid") Integer since,
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws BitcoindeException, IOException;

  @GET
  @Path("account")
  BitcoindeAccountWrapper getAccount(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws BitcoindeException, IOException;

  @GET
  @Path("orders/my_own")
  BitcoindeMyOpenOrdersWrapper getOrders(
      @HeaderParam("X-API-KEY") String apiKey,
      @HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
      @HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest)
      throws BitcoindeException, IOException;
}