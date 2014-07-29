package com.xeiam.xchange.justcoin;

import java.io.IOException;
import java.math.BigDecimal;

import javax.ws.rs.Consumes;
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

import com.xeiam.xchange.justcoin.dto.PostCreateResponse;
import com.xeiam.xchange.justcoin.dto.account.JustcoinBalance;
import com.xeiam.xchange.justcoin.dto.account.JustcoinDepositAddress;
import com.xeiam.xchange.justcoin.dto.trade.in.OrderReq;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinOrder;
import com.xeiam.xchange.justcoin.dto.trade.out.JustcoinTrade;

/**
 * @author jamespedwards42
 */
@Path("api/v1")
@Produces(MediaType.APPLICATION_JSON)
public interface JustcoinAuthenticated extends Justcoin {

  @GET
  @Path("balances")
  public JustcoinBalance[] getBalances(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @GET
  @Path("{currency}/address")
  public JustcoinDepositAddress getDepositAddress(final @PathParam("currency") String currency, final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey)
      throws IOException;

  @POST
  @Path("{currency}/out")
  @Consumes(MediaType.APPLICATION_JSON)
  public PostCreateResponse withdraw(final @PathParam("currency") String currency, final @FormParam("address") String address, final @FormParam("amount") BigDecimal amount,
      final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @GET
  @Path("orders")
  public JustcoinOrder[] getOrders(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @GET
  @Path("orders/history")
  public JustcoinTrade[] getOrderHistory(final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public PostCreateResponse createMarketOrder(final @FormParam("market") String market, final @FormParam("type") String orderType, final @FormParam("amount") BigDecimal amount,
      final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @POST
  @Path("orders")
  @Consumes(MediaType.APPLICATION_JSON)
  public PostCreateResponse createLimitOrder(final OrderReq request, final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;

  @DELETE
  @Path("orders/{orderId}")
  public void cancelOrder(final @PathParam("orderId") String orderId, final @HeaderParam("Authorization") String auth, final @QueryParam("key") String apiKey) throws IOException;
}
