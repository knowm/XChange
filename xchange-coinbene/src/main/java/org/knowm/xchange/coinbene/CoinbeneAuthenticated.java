package org.knowm.xchange.coinbene;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneLimitOrder;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrderResponse;
import org.knowm.xchange.coinbene.dto.trading.CoinbeneOrders;

@Path("v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CoinbeneAuthenticated extends Coinbene {
  /**
   * Retrieves account balance.
   *
   * @param jsonNode body JSON
   */
  @POST
  @Path("trade/balance")
  CoinbeneCoinBalances getBalance(JsonNode jsonNode) throws IOException, CoinbeneException;

  /**
   * Places on order.
   *
   * @param jsonNode body JSON
   */
  @POST
  @Path("trade/order/place")
  CoinbeneOrderResponse placeOrder(JsonNode jsonNode) throws IOException, CoinbeneException;

  /**
   * Retrieves order status info.
   *
   * @param jsonNode body JSON
   */
  @POST
  @Path("trade/order/info")
  CoinbeneLimitOrder.Container getOrderStatus(JsonNode jsonNode)
      throws IOException, CoinbeneException;

  /**
   * Cancels an order.
   *
   * @param jsonNode body JSON
   */
  @POST
  @Path("trade/order/cancel")
  CoinbeneOrderResponse cancelOrder(JsonNode jsonNode) throws IOException, CoinbeneException;

  /**
   * Retrieves open orders.
   *
   * @param jsonNode body JSON
   */
  @POST
  @Path("trade/order/open-orders")
  CoinbeneOrders.Container getOpenOrders(JsonNode jsonNode) throws IOException, CoinbeneException;
}
