package org.knowm.xchange.gemini.v1.service;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;

public class GeminiTradeServiceTest extends BaseWiremockTest {

  private GeminiTradeService classUnderTest;

  public static final String WIREMOCK_FILES_PATH = "__files";
  private static final String ORDERS_FILE_NAME = "example-open-orders-data.json";

  @Before
  public void setup() {
    classUnderTest = (GeminiTradeService) createExchange().getTradeService();
  }

  @Test
  public void ordersTest() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonRoot =
        mapper.readTree(
            this.getClass().getResource("/" + WIREMOCK_FILES_PATH + "/" + ORDERS_FILE_NAME));

    stubFor(
        post(urlPathEqualTo("/v1/orders"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile(ORDERS_FILE_NAME)));

    OpenOrders openOrders = classUnderTest.getOpenOrders();

    assertThat(openOrders).isNotNull();
    assertThat(openOrders.getOpenOrders()).hasSize(jsonRoot.size());
    LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
    assertThat(firstOrder).isNotNull();
    assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
    assertThat(firstOrder.getId()).isNotBlank();
    assertThat(firstOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);

    LimitOrder secondOrder = openOrders.getOpenOrders().get(1);
    assertThat(secondOrder).isNotNull();
    assertThat(secondOrder.getOriginalAmount()).isNotNull().isPositive();
    assertThat(secondOrder.getId()).isNotBlank();
    assertThat(secondOrder.getInstrument()).isEqualTo(CurrencyPair.LTC_BTC);
  }

  @Test
  public void openOrdersByCurrencyPairTest() throws Exception {
    stubFor(
        post(urlPathEqualTo("/v1/orders"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile(ORDERS_FILE_NAME)));

    DefaultOpenOrdersParamCurrencyPair defaultOpenOrdersParamCurrencyPair =
        new DefaultOpenOrdersParamCurrencyPair(CurrencyPair.BTC_USD);

    OpenOrders openOrders = classUnderTest.getOpenOrders(defaultOpenOrdersParamCurrencyPair);
    assertThat(openOrders).isNotNull();
    assertThat(openOrders.getOpenOrders().size()).isEqualTo(1);
    LimitOrder firstOrder = openOrders.getOpenOrders().get(0);
    assertThat(firstOrder).isNotNull();
    assertThat(firstOrder.getOriginalAmount()).isNotNull().isPositive();
    assertThat(firstOrder.getId()).isNotBlank();
    assertThat(firstOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USD);
  }
}
