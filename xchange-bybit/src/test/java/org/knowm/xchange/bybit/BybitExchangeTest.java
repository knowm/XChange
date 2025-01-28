package org.knowm.xchange.bybit;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.bybit.BybitResilience.POSITION_SET_LEVERAGE_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.POSITION_SET_LEVERAGE_LINEAR_RATE_LIMITER;

import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.service.BaseWiremockTest;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BybitExchangeTest extends BaseWiremockTest {

  @Test
  public void testSymbolLoading() throws IOException {
    Exchange bybitExchange = createExchange();

    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentSpot.json5",
        "category",
        new ContainsPattern("spot"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentLinear.json5",
        "category",
        new ContainsPattern("linear"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentInverse.json5",
        "category",
        new ContainsPattern("inverse"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentOption.json5",
        "category",
        new ContainsPattern("option"));
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");

    ExchangeSpecification specification = bybitExchange.getExchangeSpecification();
    specification.setShouldLoadRemoteMetaData(true);
    bybitExchange.applySpecification(specification);

    assertThat(bybitExchange.getExchangeMetaData().getInstruments()).hasSize(4);
  }

  @Test
  public void rateLimiterTest() throws IOException {
    Exchange bybitExchange = createExchange();
    bybitExchange
        .getResilienceRegistries()
        .rateLimiters()
        .replace(
            POSITION_SET_LEVERAGE_LINEAR_RATE_LIMITER,
            RateLimiter.of(
                POSITION_SET_LEVERAGE_LINEAR_RATE_LIMITER,
                RateLimiterConfig.custom()
                    .limitRefreshPeriod(Duration.ofSeconds(1))
                    .limitForPeriod(1)
                    .timeoutDuration(Duration.ofMillis(1))
                    .build()));
    initPostStub("/v5/position/set-leverage","/setLeverage.json5");
    BybitAccountService bybitAccountService = (BybitAccountService) bybitExchange.getAccountService();
    boolean bybitSetLeverageBybitResult;
    Throwable exception = null;
    for (int i = 0; i <= 2; i++) {
      exception = catchThrowable(() -> bybitAccountService.setLeverage(new FuturesContract("ETH/USDT/PERP"), 1d));
    }
    assertThat(exception).isInstanceOf(RequestNotPermitted.class);

  }
}
