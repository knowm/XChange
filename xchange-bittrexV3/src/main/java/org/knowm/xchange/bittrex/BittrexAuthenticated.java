package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.List;
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
import org.knowm.xchange.bittrex.dto.account.BittrexAccountVolume;
import org.knowm.xchange.bittrex.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.dto.account.BittrexBalances;
import org.knowm.xchange.bittrex.dto.batch.BatchResponse;
import org.knowm.xchange.bittrex.dto.batch.order.BatchOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexNewOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrder;
import org.knowm.xchange.bittrex.dto.trade.BittrexOrders;
import si.mazi.rescu.ParamsDigest;

@Path("v3")
@Produces(MediaType.APPLICATION_JSON)
public interface BittrexAuthenticated extends Bittrex {

  @GET
  @Path("account/volume")
  BittrexAccountVolume getAccountVolume(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException;

  @POST
  @Path("batch")
  @Consumes(MediaType.APPLICATION_JSON)
  BatchResponse[] executeOrdersBatch(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BatchOrder[] batchOrders)
      throws IOException;

  @DELETE
  @Path("orders/{order_id}")
  BittrexOrder cancelOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("order_id") String accountId)
      throws IOException;

  @GET
  @Path("balances")
  BittrexBalances getBalances(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException;

  @GET
  @Path("balances/{currencySymbol}")
  BittrexBalance getBalance(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("currencySymbol") String currencySymbol)
      throws IOException;

  @GET
  @Path("orders/{orderId}")
  BittrexOrder getOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @PathParam("orderId") String orderId)
      throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  BittrexOrder placeOrder(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      BittrexNewOrder newOrderPayload)
      throws IOException;

  @GET
  @Path("orders/open")
  BittrexOrders getOpenOrders(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature)
      throws IOException;

  // V3 replacement for get order history
  @GET
  @Path("orders/closed")
  List<BittrexOrder> getClosedOrders(
      @HeaderParam("Api-Key") String apiKey,
      @HeaderParam("Api-Timestamp") Long timestamp,
      @HeaderParam("Api-Content-Hash") ParamsDigest hash,
      @HeaderParam("Api-Signature") ParamsDigest signature,
      @QueryParam("marketSymbol") String marketSymbol,
      @QueryParam("pageSize") Integer pageSize)
      throws IOException;
}
