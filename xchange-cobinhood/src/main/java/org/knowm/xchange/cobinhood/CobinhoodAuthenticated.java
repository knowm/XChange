package org.knowm.xchange.cobinhood;

import java.io.IOException;
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
import org.knowm.xchange.cobinhood.dto.CobinhoodResponse;
import org.knowm.xchange.cobinhood.dto.account.CobinhoodCoinBalances;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrder;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodOrders;
import org.knowm.xchange.cobinhood.dto.trading.CobinhoodPlaceOrderRequest;
import si.mazi.rescu.SynchronizedValueFactory;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
public interface CobinhoodAuthenticated extends Cobinhood {

  String HEADER_AUTH = "authorization";
  String HEADER_NONCE = "nonce";

  @GET
  @Path("wallet/balances")
  CobinhoodResponse<CobinhoodCoinBalances> getBalances(@HeaderParam(HEADER_AUTH) String apiKey)
      throws IOException, CobinhoodException;

  @GET
  @Path("trading/orders")
  CobinhoodResponse<CobinhoodOrders> getAllOrders(
      @HeaderParam(HEADER_AUTH) String apiKey,
      @QueryParam("trading_pair_id") String pair,
      @QueryParam("limit") Integer limit);

  /** Places a limit order. */
  @POST
  @Path("trading/orders")
  @Consumes(MediaType.APPLICATION_JSON)
  CobinhoodResponse<CobinhoodOrder.Container> placeOrder(
      @HeaderParam(HEADER_AUTH) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      CobinhoodPlaceOrderRequest placeOrderRequest)
      throws IOException, CobinhoodException;

  @DELETE
  @Path("trading/orders/{order_id}")
  CobinhoodResponse<Void> cancelOrder(
      @HeaderParam(HEADER_AUTH) String apiKey,
      @HeaderParam(HEADER_NONCE) SynchronizedValueFactory<Long> nonce,
      @PathParam("order_id") String orderId);
}
