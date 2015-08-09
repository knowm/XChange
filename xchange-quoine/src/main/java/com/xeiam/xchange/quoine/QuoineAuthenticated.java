package com.xeiam.xchange.quoine;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import com.xeiam.xchange.quoine.dto.trade.QuoineNewOrderRequest;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrderResponse;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersList;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface QuoineAuthenticated extends Quoine {

  @GET
  @Path("accounts")
  public QuoineAccountInfo getAccountInfo(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken) throws IOException;

  @GET
  @Path("trading_accounts")
  public QuoineTradingAccountInfo[] getTradingAccountInfo(@HeaderParam("X-Quoine-Device") String device,
      @HeaderParam("X-Quoine-User-Id") String userID, @HeaderParam("X-Quoine-User-Token") String userToken) throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public QuoineOrderResponse placeOrder(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken, QuoineNewOrderRequest quoineNewOrderRequest) throws IOException;

  @PUT
  @Path("orders/{order_id}/cancel")
  public QuoineOrderResponse cancelOrder(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken, @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders/{order_id}")
  public QuoineOrderDetailsResponse orderDetails(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken, @PathParam("order_id") String orderID) throws IOException;

  @GET
  @Path("orders")
  public QuoineOrdersList listOrders(@HeaderParam("X-Quoine-Device") String device, @HeaderParam("X-Quoine-User-Id") String userID,
      @HeaderParam("X-Quoine-User-Token") String userToken, @QueryParam("currency_pair_code") String currencyPair) throws IOException;

}
