package com.xeiam.xchange.vircurex;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.vircurex.dto.account.VircurexAccountInfoReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexOpenOrdersReturn;
import com.xeiam.xchange.vircurex.dto.trade.VircurexPlaceOrderReturn;

@Path("api")
@Produces(MediaType.APPLICATION_JSON)
public interface VircurexAuthenticated {

  @GET
  @Path("get_balances.json")
  VircurexAccountInfoReturn getInfo(@QueryParam("account") String aUserName, @QueryParam("id") String anId, @QueryParam("token") String aToken, @QueryParam("timestamp") String aTimestamp);

  @Path("create_order.json")
  @GET
  VircurexPlaceOrderReturn trade(@QueryParam("account") String aUserName, @QueryParam("id") String anId, @QueryParam("token") String aToken, @QueryParam("timestamp") String aTimestamp,
      @QueryParam("ordertype") String anOrderType, @QueryParam("amount") String anAmount, @QueryParam("currency1") String aCurrency, @QueryParam("unitprice") String aPrice,
      @QueryParam("currency2") String aTransactionCurrency);

  @Path("release_order.json")
  @GET
  VircurexPlaceOrderReturn release(@QueryParam("account") String apiKey, @QueryParam("id") String nonce, @QueryParam("token") String token, @QueryParam("timestamp") String timestamp,
      @QueryParam("orderid") String orderId);

  @Path("read_orders.json")
  @GET
  VircurexOpenOrdersReturn getOpenOrders(@QueryParam("account") String apiKey, @QueryParam("id") String nonce, @QueryParam("token") String token, @QueryParam("timestamp") String timestamp,
      @QueryParam("otype") int releaseStatus);
}
