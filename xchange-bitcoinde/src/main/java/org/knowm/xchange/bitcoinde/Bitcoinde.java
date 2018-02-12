package org.knowm.xchange.bitcoinde;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
  BitcoindeIdResponse deleteOrder(@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
		@PathParam("order_id") String order_id,
		@PathParam("trading_pair") String trading_pair) throws BitcoindeException,
		IOException;


  @POST
  @Path("orders")
  BitcoindeIdResponse createOrder(
		@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest,
		@FormParam("max_amount") BigDecimal max_amount,
		@FormParam("price") BigDecimal price,
		@FormParam("trading_pair") String trading_pair,
		@FormParam("type") String type) throws BitcoindeException,
		IOException;

  @GET
  @Path("orders/compact")
  BitcoindeOrderbookWrapper getOrderBook(
		@QueryParam("trading_pair") String trading_pair,
		@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws BitcoindeException,
		IOException;

  @GET
  @Path("trades/history")
  BitcoindeTradesWrapper getTrades(
		@QueryParam("trading_pair") String trading_pair,
		@QueryParam("since_tid") Integer since,
		@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws BitcoindeException,
		IOException;

  @GET
  @Path("account")
  BitcoindeAccountWrapper getAccount(
		@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws BitcoindeException,
		IOException;

  @GET
  @Path("orders/my_own")
  BitcoindeMyOpenOrdersWrapper getOrders(
		@HeaderParam("X-API-KEY") String apiKey,
		@HeaderParam("X-API-NONCE") SynchronizedValueFactory<Long> nonce,
		@HeaderParam("X-API-SIGNATURE") ParamsDigest paramsDigest) throws BitcoindeException,
		IOException;
}
