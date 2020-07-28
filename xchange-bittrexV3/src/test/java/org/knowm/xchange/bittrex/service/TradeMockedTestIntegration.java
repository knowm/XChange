package org.knowm.xchange.bittrex.service;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bittrex.BittrexConstants;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** @author walec51 */
public class TradeMockedTestIntegration extends BaseMockedTestIntegration {

  private static BittrexTradeService tradeService;
  private static final String NEWORDER_FILE_NAME = "newOrder.json";
  private static final String OPENORDERS_FILE_NAME = "openOrders.json";

  @Before
  public void setUp() {
    tradeService = (BittrexTradeService) createExchange().getTradeService();
  }

  @Test
  public void placeOrderTest() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonRoot =
        mapper.readTree(
            this.getClass().getResource("/" + WIREMOCK_FILES_PATH + "/" + NEWORDER_FILE_NAME));

    stubFor(
        post(urlPathEqualTo("/v3/orders"))
            .withRequestBody(equalToJson(jsonRoot.toString()))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("placedorder.json")));

    Order.OrderType type =
        BittrexConstants.BUY.equals(jsonRoot.get("direction").asText())
            ? Order.OrderType.BID
            : Order.OrderType.ASK;
    String[] currencyPairSplit = jsonRoot.get("marketSymbol").asText().split("-");
    CurrencyPair market = new CurrencyPair(currencyPairSplit[0], currencyPairSplit[1]);
    BigDecimal price = new BigDecimal(jsonRoot.get("limit").asText());
    BigDecimal quantity = new BigDecimal(jsonRoot.get("quantity").asText());
    String orderId =
        tradeService.placeLimitOrder(
            new LimitOrder.Builder(type, market)
                .limitPrice(price)
                .originalAmount(quantity)
                .build());
    assertThat(orderId).isNotNull().isNotEmpty();
  }

  @Test
  public void openOrdersTest() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonRoot =
        mapper.readTree(
            this.getClass().getResource("/" + WIREMOCK_FILES_PATH + "/" + OPENORDERS_FILE_NAME));
    stubFor(
        get(urlPathEqualTo("/v3/orders/open"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("openOrders.json")));

    OpenOrders openOrders = tradeService.getOpenOrders();
    assertThat(openOrders).isNotNull();
    assertThat(openOrders.getOpenOrders()).hasSize(jsonRoot.size());
    LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
    assertThat(firstOrder).isNotNull();
    assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
    assertThat(firstOrder.getId()).isNotBlank();
  }

  @Test
  public void tradeHistoryTest() throws Exception {
    stubFor(
        get(urlPathEqualTo("/v3/orders/closed"))
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
