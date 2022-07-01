package org.knowm.xchange.binance;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class AbstractResilienceTest {

  @Rule public WireMockRule wireMockRule = new WireMockRule();

  public static int READ_TIMEOUT_MS = 1000;

  @Before
  public void resertResilienceRegistries() {
    BinanceExchange.resetResilienceRegistries();
  }

  protected BinanceExchange createExchangeWithRetryEnabled() {
    return createExchange(true, false);
  }

  protected BinanceExchange createExchangeWithRetryDisabled() {
    return createExchange(false, false);
  }

  protected BinanceExchange createExchangeWithRateLimiterEnabled() {
    return createExchange(false, true);
  }

  protected BinanceExchange createExchange(boolean retryEnabled, boolean rateLimiterEnabled) {
    BinanceExchange exchange =
        (BinanceExchange)
            ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.port() + "/");
    specification.setPort(wireMockRule.port());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(READ_TIMEOUT_MS);
    specification.getResilience().setRetryEnabled(retryEnabled);
    specification.getResilience().setRateLimiterEnabled(rateLimiterEnabled);
    exchange.applySpecification(specification);
    return exchange;
  }
}
