package org.knowm.xchange.binance;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.service.BinanceAccountService;

public class BinanceExchangeIntegration {
  protected static BinanceExchange exchange;

  @RegisterExtension
  public WireMockExtension wireMockRule =
      WireMockExtension.newInstance()
          .options(wireMockConfig().dynamicPort())
          .configureStaticDsl(true)
          .build();

  @BeforeAll
  static void beforeClass() throws Exception {
    createExchange();
  }

  @Test
  void sytemStatus() throws Exception {
    assumeProduction();
    BinanceSystemStatus systemStatus =
        ((BinanceAccountService) exchange.getAccountService()).getSystemStatus();
    assertThat(systemStatus).isNotNull();
    assertThat(systemStatus.getStatus()).isNotEmpty();
  }

  protected static void createExchange() throws Exception {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceExchange.class);
    ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
    boolean useSandbox =
        Boolean.parseBoolean(System.getProperty(Exchange.USE_SANDBOX, Boolean.FALSE.toString()));
    spec.setExchangeSpecificParametersItem(Exchange.USE_SANDBOX, useSandbox);
    exchange.applySpecification(spec);
  }

  protected void assumeProduction() {
    Assumptions.assumeFalse(exchange.usingSandbox(), "Using sandbox");
  }

  protected BinanceExchange createExchangeMocked() {
    BinanceExchange exchangeMocked =
        ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceExchange.class);
    ExchangeSpecification specification = exchangeMocked.getDefaultExchangeSpecification();
    specification.setHost("localhost");
    specification.setSslUri("http://localhost:" + wireMockRule.getPort() + "/");
    specification.setPort(wireMockRule.getPort());
    specification.setShouldLoadRemoteMetaData(false);
    exchangeMocked.applySpecification(specification);
    return exchangeMocked;
  }
}
