package org.knowm.xchange.binance.us;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.BinanceUsExchange;
import org.knowm.xchange.binance.dto.meta.BinanceSystemStatus;
import org.knowm.xchange.binance.service.BinanceUsAccountService;

public class BinanceUsExchangeIntegration {
  protected static BinanceUsExchange exchange;

  @BeforeClass
  public static void beforeClass() throws Exception {
    createExchange();
  }

  @Test
  public void testSetupIsCorrect() {
    ExchangeSpecification specification = exchange.getDefaultExchangeSpecification();
    assertThat(specification.getExchangeName().equalsIgnoreCase("Binance US")).isTrue();
    assertThat(specification.getSslUri().equalsIgnoreCase("https://api.binance.us")).isTrue();
    assertThat(specification.getHost().equalsIgnoreCase("www.binance.us")).isTrue();
    assertThat(specification.getExchangeDescription().equalsIgnoreCase("Binance US Exchange."))
        .isTrue();
    assertThat(specification.getExchangeClass().equals(BinanceUsExchange.class)).isTrue();
    assertThat(specification.getResilience()).isNotNull();
  }

  @Test
  public void testSystemStatus() throws IOException {
    assumeProduction();
    BinanceSystemStatus systemStatus =
        ((BinanceUsAccountService) exchange.getAccountService()).getSystemStatus();
    assertThat(systemStatus).isNotNull();
    // Not yet supported by binance.us
    assertThat(systemStatus.getStatus()).isNull();
  }

  protected static void createExchange() throws Exception {
    exchange = ExchangeFactory.INSTANCE.createExchangeWithoutSpecification(BinanceUsExchange.class);
    ExchangeSpecification spec = exchange.getDefaultExchangeSpecification();
    boolean useSandbox =
        Boolean.parseBoolean(
            System.getProperty(
                BinanceExchange.SPECIFIC_PARAM_USE_SANDBOX, Boolean.FALSE.toString()));
    spec.setExchangeSpecificParametersItem(BinanceExchange.SPECIFIC_PARAM_USE_SANDBOX, useSandbox);
    exchange.applySpecification(spec);
  }

  protected void assumeProduction() {
    Assume.assumeFalse("Using sandbox", exchange.usingSandbox());
  }
}
