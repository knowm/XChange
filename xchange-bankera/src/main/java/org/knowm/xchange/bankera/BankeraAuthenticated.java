package org.knowm.xchange.bankera;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.bankera.dto.BankeraException;
import org.knowm.xchange.bankera.dto.BaseBankeraRequest;
import org.knowm.xchange.bankera.dto.CreateOrderRequest;
import org.knowm.xchange.bankera.dto.account.BankeraUserInfo;
import org.knowm.xchange.bankera.dto.trade.BankeraOpenOrders;
import org.knowm.xchange.bankera.dto.trade.BankeraOrder;
import org.knowm.xchange.bankera.dto.trade.BankeraUserTrades;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public interface BankeraAuthenticated extends Bankera {
  @GET
  @Path("users/info")
  BankeraUserInfo getUserInfo(@HeaderParam("Authorization") String authorization)
      throws BankeraException, IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("orders/new")
  BankeraOrder placeOrder(
      @HeaderParam("Authorization") String authorization, CreateOrderRequest request)
      throws BankeraException, IOException;

  @GET
  @Path("orders/open")
  BankeraOpenOrders getOpenOrders(
      @HeaderParam("Authorization") String authorization,
      @QueryParam("market") String market,
      @QueryParam("limit") Integer limit,
      @QueryParam("offset") Integer offset)
      throws BankeraException, IOException;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("orders/cancel/{orderId}")
  BankeraOrder cancelOrder(
      @HeaderParam("Authorization") String authorization,
      @PathParam("orderId") Long orderId,
      BaseBankeraRequest request)
      throws BankeraException, IOException;

  @POST
  @Path("orders/cancel/all")
  List<BankeraOrder> cancelAllOrders(@HeaderParam("Authorization") String authorization)
      throws BankeraException, IOException;

  @GET
  @Path("trades/history")
  BankeraUserTrades getUserTrades(
      @HeaderParam("Authorization") String authorization, @QueryParam("market") String market)
      throws BankeraException, IOException;

  @GET
  @Path("orders/details")
  BankeraOrder getUserOrder(
      @HeaderParam("Authorization") String authorization, @QueryParam("orderId") String orderId)
      throws BankeraException, IOException;
}
