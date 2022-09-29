package org.knowm.xchange.bitrue.service.marketdata;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.junit.Test;
import org.knowm.xchange.bitrue.AbstractResilienceTest;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.BitrueResilience;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.time.Duration;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class MarketDataServiceResilienceTest extends AbstractResilienceTest {

  @Test
  public void shouldSucceedIfFirstCallTimeoutedAndRetryIsEnabled() throws Exception {
    // given
    MarketDataService service = createExchangeWithRetryEnabled().getMarketDataService();
    stubForTicker24WithFirstCallTimetoutAndSecondSuccessful();

    // when
    Ticker ticker = service.getTicker(new CurrencyPair("BTR", "BTC"));

    // then
    assertThat(ticker.getLast()).isEqualByComparingTo("0.02000200");
  }

  @Test
  public void shouldFailIfFirstCallTimeoutedAndRetryIsDisabled() throws Exception {
    // given
    MarketDataService service = createExchangeWithRetryDisabled().getMarketDataService();
    stubForTicker24WithFirstCallTimetoutAndSecondSuccessful();

    // when
    Throwable exception = catchThrowable(() -> service.getTicker(new CurrencyPair("BTR", "BTC")));

    // then
    assertThat(exception).isInstanceOf(IOException.class);
  }

  @Test(timeout = 2000)
  public void shouldGetMaxDepthTwoTimesWithoutDelayWithDefaultRateLimiter() throws Exception {
    // given
    BitrueExchange exchange = createExchangeWithRateLimiterEnabled();
    MarketDataService service = exchange.getMarketDataService();
    stubForDepth();

    // when
    OrderBook orderBook = service.getOrderBook(CurrencyPair.ETH_BTC, 5000);
    orderBook = service.getOrderBook(CurrencyPair.ETH_BTC, 5000);

    // then
    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotEmpty();
  }

  @Test(timeout = 2000)
  public void shouldGetTimeoutOnSecondMaxDepthVeryRestrictiveCustomRateLimiter() throws Exception {
    // given
    BitrueExchange exchange = createExchangeWithRateLimiterEnabled();
    exchange
        .getResilienceRegistries()
        .rateLimiters()
        .replace(
            BitrueResilience.REQUEST_WEIGHT_RATE_LIMITER,
            RateLimiter.of(
                BitrueResilience.REQUEST_WEIGHT_RATE_LIMITER,
                RateLimiterConfig.custom()
                    .limitRefreshPeriod(Duration.ofMinutes(1))
                    .limitForPeriod(80)
                    .timeoutDuration(Duration.ofMillis(10))
                    .build()));
    MarketDataService service = exchange.getMarketDataService();
    stubForDepth();

    // when
    service.getOrderBook(CurrencyPair.ETH_BTC, 5000);
    Throwable exception = catchThrowable(() -> service.getOrderBook(CurrencyPair.ETH_BTC, 5000));

    // then
    assertThat(exception).isInstanceOf(RequestNotPermitted.class);
  }

  private void stubForTicker24WithFirstCallTimetoutAndSecondSuccessful() {
    stubFor(
        get(urlPathEqualTo("/api/v1/ticker/24hr"))
            .inScenario("Retry read")
            .whenScenarioStateIs(STARTED)
            .willReturn(aResponse().withFixedDelay(READ_TIMEOUT_MS * 2).withStatus(500))
            .willSetStateTo("After fail"));
    stubFor(
        get(urlPathEqualTo("/api/v1/ticker/24hr"))
            .inScenario("Retry read")
            .whenScenarioStateIs("After fail")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("single-24hr-ticker.json")));
  }

  private void stubForDepth() {
    stubFor(
        get(urlPathEqualTo("/api/v1/depth"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("depth.json")));
  }
}
