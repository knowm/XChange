package org.knowm.xchange.binance.service.trade;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.binance.AbstractResilienceTest;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class TradeServiceResilienceTest extends AbstractResilienceTest {

  @Before
  public void resertResilienceRegistries() {
    BinanceExchange.resetResilienceRegistries();
  }

  @Test
  public void shouldSucceedIfFirstCallTimeoutedAndRetryIsEnabled() throws Exception {
    // given
    TradeService service = createExchangeWithRetryEnabled().getTradeService();
    stubForOpenOrdersWithFirstCallTimetoutAndSecondSuccessful();
    OpenOrdersParams params = service.createOpenOrdersParams();
    ((OpenOrdersParamCurrencyPair) params).setCurrencyPair(CurrencyPair.LTC_BTC);

    // when
    OpenOrders openOrders = service.getOpenOrders(params);

    // then
    assertThat(openOrders.getOpenOrders())
        .hasSize(1)
        .first()
        .extracting(Order::getCurrencyPair)
        .isEqualTo(CurrencyPair.LTC_BTC);
  }

  @Test
  public void shouldFailIfFirstCallTimeoutedAndRetryIsDisabled() throws Exception {
    // given
    TradeService service = createExchangeWithRetryDisabled().getTradeService();
    stubForOpenOrdersWithFirstCallTimetoutAndSecondSuccessful();
    OpenOrdersParams params = service.createOpenOrdersParams();
    ((OpenOrdersParamCurrencyPair) params).setCurrencyPair(CurrencyPair.LTC_BTC);

    // when
    Throwable exception = catchThrowable(() -> service.getOpenOrders(params));

    // then
    assertThat(exception).isInstanceOf(IOException.class);
  }

  private void stubForOpenOrdersWithFirstCallTimetoutAndSecondSuccessful() {
    stubFor(
        get(urlPathEqualTo("/api/v3/openOrders"))
            .inScenario("Retry read")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withFixedDelay(READ_TIMEOUT_MS * 2).withStatus(500))
            .willSetStateTo("After fail"));
    stubFor(
        get(urlPathEqualTo("/api/v3/openOrders"))
            .inScenario("Retry read")
            .whenScenarioStateIs("After fail")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("open-orders.json")));
  }
}
