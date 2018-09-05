package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** @author walec51 */
public class TradeMockedIntegrationTest extends BaseMockedIntegrationTest {

  private static BittrexTradeService tradeService;

  @Before
  public void setUp() {
    tradeService = (BittrexTradeService) createExchange().getTradeService();
  }

  @Test
  public void placeOrderTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/market/buylimit"))
            .withQueryParam("market", equalTo("BTC-ETH"))
            .withQueryParam("quantity", equalTo("1"))
            .withQueryParam("rate", equalTo("0.004"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("placedorder.json")));
    String orderId =
        tradeService.placeLimitOrder(
            new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.ETH_BTC)
                .limitPrice(new BigDecimal("0.004"))
                .originalAmount(new BigDecimal("1"))
                .build());
    assertThat(orderId).isNotNull().isNotEmpty();
  }

  @Test
  public void openOrdersTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/market/getopenorders"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("openorders.json")));
    OpenOrders openOrders = tradeService.getOpenOrders();
    assertThat(openOrders).isNotNull();
    assertThat(openOrders.getOpenOrders()).isNotEmpty();
    LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
    assertThat(firstOrder).isNotNull();
    assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
    assertThat(firstOrder.getId()).isNotBlank();
  }

  @Test
  public void tradeHistoryTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/api/v1.1/account/getorderhistory"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("orderhistory.json")));
    TradeHistoryParams params = tradeService.createTradeHistoryParams();
    UserTrades tradeHistory = tradeService.getTradeHistory(params);
    assertThat(tradeHistory).isNotNull();
    assertThat(tradeHistory.getUserTrades()).isNotEmpty();
    UserTrade trade = tradeHistory.getUserTrades().get(0);
    assertThat(trade).isNotNull();
    assertThat(trade.getOriginalAmount()).isNotNull().isPositive();
    assertThat(trade.getPrice()).isNotNull().isPositive();
  }
}
