package org.knowm.xchange.mexc.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;

public class MEXCTradeServiceTest extends BaseWiremockTest {

  @Test
  public void testGetMEXCOrder() throws IOException {
    Exchange mexcExchange = createExchange();
    MEXCTradeService mexcAccountService = new MEXCTradeService(mexcExchange);

    String orderDetails =
        "{\n"
            + "    \"code\": 200,\n"
            + "    \"data\": [\n"
            + "        {\n"
            + "            \"id\": \"504feca6ba6349e39c82262caf0be3f4\",\n"
            + "            \"symbol\": \"MX_ETH\",\n"
            + "            \"price\": \"0.000901\",\n"
            + "            \"quantity\": \"300000\",\n"
            + "            \"state\": \"NEW\",\n"
            + "            \"type\": \"BID\",\n"
            + "            \"deal_quantity\": \"0\",\n"
            + "            \"deal_amount\": \"0\",\n"
            + "            \"create_time\": 1573117266000\n"
            + "        },\n"
            + "        {\n"
            + "            \"id\": \"72872b6ae721480ca4fe0f265d29dfee\",\n"
            + "            \"symbol\": \"MX_ETH\",\n"
            + "            \"price\": \"0.000907\",\n"
            + "            \"quantity\": \"300000\",\n"
            + "            \"state\": \"FILLED\",\n"
            + "            \"type\": \"ASK\",\n"
            + "            \"deal_quantity\": \"0.5\",\n"
            + "            \"deal_amount\": \"0.25\",\n"
            + "            \"create_time\": 1573117267000\n"
            + "        }\n"
            + "    ]\n"
            + "}";

    stubFor(
        get(urlPathEqualTo("/open/api/v2/order/query"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderDetails)));

    Collection<Order> orders =
        mexcAccountService.getOrder(
            "504feca6ba6349e39c82262caf0be3f4", "72872b6ae721480ca4fe0f265d29dfee");
    assertThat(orders.size()).isEqualTo(2);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("MX", "ETH"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0"));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.NEW);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("300000"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0"));

    order = (Order) orders.toArray()[1];
    assertThat(order.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("MX", "ETH"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0.50"));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("300000"));
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("0.5"));
  }

  @Test
  public void testPlaceMEXCOrder() throws IOException {
    Exchange mexcExchange = createExchange();
    MEXCTradeService mexcTradeService = new MEXCTradeService(mexcExchange);

    String orderPlacementResponse =
        "{\n"
            + "    \"code\": 200,\n"
            + "    \"data\": \"c8663a12a2fc457fbfdd55307b463495\"\n"
            + "}";

    stubFor(
        post(urlPathEqualTo("/open/api/v2/order/place"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderPlacementResponse)));

    String orderId =
        mexcTradeService.placeLimitOrder(
            new LimitOrder(
                Order.OrderType.ASK,
                new BigDecimal("300"),
                new CurrencyPair("COIN", "USDT"),
                null,
                null,
                new BigDecimal("0.25")));

    assertThat(orderId).isEqualTo("c8663a12a2fc457fbfdd55307b463495");
  }
}
