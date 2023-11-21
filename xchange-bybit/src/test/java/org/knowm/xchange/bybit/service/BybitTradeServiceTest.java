package org.knowm.xchange.bybit.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import jakarta.ws.rs.core.Response.Status;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;

public class BybitTradeServiceTest extends BaseWiremockTest {

  @Test
  public void testGetBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeService bybitAccountService = new BybitTradeService(bybitExchange);

    String orderDetails = "{\n" +
        "   \"ret_code\":0,\n" +
        "   \"ret_msg\":\"\",\n" +
        "   \"ext_code\":null,\n" +
        "   \"ext_info\":null,\n" +
        "   \"result\":{\n" +
        "      \"accountId\":\"123456789\",\n" +
        "      \"exchangeId\":\"301\",\n" +
        "      \"symbol\":\"COINUSDT\",\n" +
        "      \"symbolName\":\"COINUSDT\",\n" +
        "      \"orderLinkId\":\"1234567891011121\",\n" +
        "      \"orderId\":\"1234567891011121314\",\n" +
        "      \"price\":\"0\",\n" +
        "      \"origQty\":\"352\",\n" +
        "      \"executedQty\":\"352\",\n" +
        "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
        "      \"avgPrice\":\"0.001619\",\n" +
        "      \"status\":\"FILLED\",\n" +
        "      \"timeInForce\":\"GTC\",\n" +
        "      \"type\":\"MARKET\",\n" +
        "      \"side\":\"SELL\",\n" +
        "      \"stopPrice\":\"0.0\",\n" +
        "      \"icebergQty\":\"0.0\",\n" +
        "      \"time\":\"1655997749601\",\n" +
        "      \"updateTime\":\"1655997749662\",\n" +
        "      \"isWorking\":true,\n" +
        "      \"locked\":\"0\"\n" +
        "   }\n" +
        "}";

    stubFor(
        get(urlPathEqualTo("/spot/v1/order"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderDetails)
            )
    );

    Collection<Order> orders = bybitAccountService.getOrder("1234567891011121314");
    assertThat(orders.size()).isEqualTo(1);

    Order order = (Order) orders.toArray()[0];
    assertThat(order.getType()).isEqualTo(Order.OrderType.ASK);
    assertThat(order.getInstrument()).isEqualTo(new CurrencyPair("COIN", "USDT"));
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("0.001619"));
    assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.FILLED);
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("352"));

  }


  @Test
  public void testPlaceBybitOrder() throws IOException {
    Exchange bybitExchange = createExchange();
    BybitTradeService bybitAccountService = new BybitTradeService(bybitExchange);

    String orderPlacementResponse = "{\n" +
        "   \"ret_code\":0,\n" +
        "   \"ret_msg\":\"\",\n" +
        "   \"ext_code\":null,\n" +
        "   \"ext_info\":null,\n" +
        "   \"result\":{\n" +
        "      \"accountId\":\"28649557\",\n" +
        "      \"exchangeId\":\"301\",\n" +
        "      \"symbol\":\"COINUSDT\",\n" +
        "      \"symbolName\":\"COINUSDT\",\n" +
        "      \"orderLinkId\":\"1655997749596563\",\n" +
        "      \"orderId\":\"1184989442799045889\",\n" +
        "      \"price\":\"0\",\n" +
        "      \"origQty\":\"352\",\n" +
        "      \"executedQty\":\"352\",\n" +
        "      \"cummulativeQuoteQty\":\"0.569888\",\n" +
        "      \"avgPrice\":\"0.001619\",\n" +
        "      \"status\":\"FILLED\",\n" +
        "      \"timeInForce\":\"GTC\",\n" +
        "      \"type\":\"MARKET\",\n" +
        "      \"side\":\"SELL\",\n" +
        "      \"stopPrice\":\"0.0\",\n" +
        "      \"icebergQty\":\"0.0\",\n" +
        "      \"time\":\"1655997749601\",\n" +
        "      \"updateTime\":\"1655997749662\",\n" +
        "      \"isWorking\":true,\n" +
        "      \"locked\":\"0\"\n" +
        "   }\n" +
        "}";

    stubFor(
        post(urlPathEqualTo("/spot/v1/order"))
            .willReturn(
                aResponse()
                    .withStatus(Status.OK.getStatusCode())
                    .withHeader("Content-Type", "application/json")
                    .withBody(orderPlacementResponse)
            )
    );

    String orderId = bybitAccountService.placeMarketOrder(
        new MarketOrder(Order.OrderType.ASK, new BigDecimal("300"), new CurrencyPair("COIN", "USDT"))
    );

    assertThat(orderId).isEqualTo("1184989442799045889");

  }

}