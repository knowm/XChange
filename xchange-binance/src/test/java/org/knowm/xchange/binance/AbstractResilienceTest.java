package org.knowm.xchange.binance;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;

public class AbstractResilienceTest {

  @RegisterExtension
  public WireMockExtension wireMockRule =
      WireMockExtension.newInstance()
          .options(wireMockConfig().dynamicPort())
          .configureStaticDsl(true)
          .build();

  public static int READ_TIMEOUT_MS = 1000;

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
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceExchange.class);
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.getPort() + "/");
    specification.setPort(wireMockRule.getPort());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setHttpReadTimeout(READ_TIMEOUT_MS);
    specification.getResilience().setRetryEnabled(retryEnabled);
    specification.getResilience().setRateLimiterEnabled(rateLimiterEnabled);
    exchange.applySpecification(specification);
    return exchange;
  }
}
