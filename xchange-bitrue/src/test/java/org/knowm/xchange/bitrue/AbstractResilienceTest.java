package org.knowm.xchange.bitrue;

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
    BitrueExchange.resetResilienceRegistries();
  }

  protected BitrueExchange createExchangeWithRetryEnabled() {
    return createExchange(true, false);
  }

  protected BitrueExchange createExchangeWithRetryDisabled() {
    return createExchange(false, false);
  }

  protected BitrueExchange createExchangeWithRateLimiterEnabled() {
    return createExchange(false, true);
  }

  protected BitrueExchange createExchange(boolean retryEnabled, boolean rateLimiterEnabled) {
    BitrueExchange exchange =
        (BitrueExchange)
            ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BitrueExchange.class);
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
