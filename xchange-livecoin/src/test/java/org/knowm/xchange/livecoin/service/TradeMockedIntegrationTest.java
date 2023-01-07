package org.knowm.xchange.livecoin.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

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

  private static LivecoinTradeService tradeService;

  @Before
  public void setUp() {
    tradeService = (LivecoinTradeService) createExchange().getTradeService();
  }

  @Test
  public void placeOrderTest() throws Exception {
    stubFor(
        post(urlPathEqualTo("/exchange/buylimit"))
            .withRequestBody(containing("currencyPair=ETH%2FBTC"))
            .withRequestBody(containing("price=0.004"))
            .withRequestBody(containing("quantity=1"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("placed_order.json")));
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
        get(urlPathEqualTo("/exchange/client_orders"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("client_orders.json")));
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
        get(urlPathEqualTo("/payment/history/transactions"))
            .withQueryParam("start", matching("[0-9]+"))
            .withQueryParam("end", matching("[0-9]+"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("transactions_buy_sell.json")));
    TradeHistoryParams params = tradeService.createTradeHistoryParams();
    UserTrades tradeHistory = tradeService.getTradeHistory(params);
    assertThat(tradeHistory).isNotNull();
    assertThat(tradeHistory.getUserTrades()).isNotEmpty();
    UserTrade trade = tradeHistory.getUserTrades().get(0);
    assertThat(trade).isNotNull();
    assertThat(trade.getType()).isNotNull();
    assertThat(trade.getOriginalAmount()).isNotNull().isPositive();
    assertThat(trade.getPrice()).isNotNull().isPositive();
  }
}
