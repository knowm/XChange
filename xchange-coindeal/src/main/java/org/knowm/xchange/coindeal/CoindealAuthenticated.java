package org.knowm.xchange.coindeal;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coindeal.dto.account.CoindealBalance;
import org.knowm.xchange.coindeal.dto.trade.CoindealOrder;
import org.knowm.xchange.coindeal.dto.trade.CoindealTradeHistory;
import si.mazi.rescu.ParamsDigest;

@Path("api/")
@Produces(MediaType.APPLICATION_JSON)
public interface CoindealAuthenticated extends Coindeal {

  String HEADER_AUTH = "authorization";

  @GET
  @Path("v1/trading/balance")
  List<CoindealBalance> getBalances(@HeaderParam(HEADER_AUTH) ParamsDigest credentials)
      throws IOException;

  @GET
  @Path("v2/history/trades")
  List<CoindealTradeHistory> getTradeHistory(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
      @QueryParam("symbol") String currencyPair,
      @QueryParam("limit") int limit)
      throws IOException;

  @GET
  @Path("v1/order")
  List<CoindealOrder> getActiveOrders(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials, @QueryParam("symbol") String currencyPair)
      throws IOException;

  @POST
  @Path("v1/order")
  CoindealOrder placeOrder(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
      @FormParam("symbol") String symbol,
      @FormParam("side") String side,
      @FormParam("type") String type,
      @FormParam("timeInForce") String timeInForce,
      @FormParam("quantity") double quantity,
      @FormParam("price") double price)
      throws IOException;

  @DELETE
  @Path("v1/order")
  List<CoindealOrder> cancelOrders(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials, @FormParam("symbol") String symbol)
      throws IOException;

  @DELETE
  @Path("v1/order/{clientOrderId}")
  CoindealOrder cancelOrderById(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
      @PathParam("clientOrderId") String cliendOrderId)
      throws IOException;

  @GET
  @Path("v1/order/{clientOrderId}")
  CoindealOrder getOrderById(
      @HeaderParam(HEADER_AUTH) ParamsDigest credentials,
      @PathParam("clientOrderId") String cliendOrderId)
      throws IOException;
}
