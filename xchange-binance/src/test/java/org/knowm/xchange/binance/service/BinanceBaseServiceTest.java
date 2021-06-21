package org.knowm.xchange.binance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceAuthenticated;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.BinanceResilience;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;

public class BinanceBaseServiceTest {
  /**
   * Tests the functionality of the {@link BinanceBaseService#getRecvWindow()} to safely obtain a
   * value for the recvWindow for various supplied inputs.
   */
  @Test
  public void testGetRecvWindow() {
    // Simple helper function that prepares a service with the "recvWindow" param and calls the
    // getter.
    Function<Object, Long> serviceBuilder =
        obj -> {
          Map<String, Object> params = new HashMap<>();
          params.put("recvWindow", obj);
          return getBaseService(params).getRecvWindow();
        };

    assertThat(serviceBuilder.apply(null)).isNull();
    assertThat(serviceBuilder.apply(1L)).isEqualTo(1L);
    assertThat(serviceBuilder.apply(123)).isEqualTo(123L);
    assertThat(serviceBuilder.apply(0.1)).isEqualTo(0L);
    assertThat(serviceBuilder.apply(4.9999)).isEqualTo(4L);
    assertThatThrownBy(() -> serviceBuilder.apply(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must be in the range");
    assertThatThrownBy(() -> serviceBuilder.apply(60001))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("must be in the range");
    assertThatThrownBy(() -> serviceBuilder.apply("hello world"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("could not be parsed");
  }

  /**
   * Constructs a simple instance of the {@link BinanceBaseService} with the exchange-specific
   * parameters set to the given map.
   *
   * @param exchangeSpecificParams The parameters to set on the base service.
   * @return The instance of the service.
   */
  private BinanceBaseService getBaseService(Map<String, Object> exchangeSpecificParams) {
    ExchangeSpecification spec = new BinanceExchange().getDefaultExchangeSpecification();
    spec.setExchangeSpecificParameters(exchangeSpecificParams);
    spec.setApiKey("abc");
    spec.setSecretKey("123");
    BinanceExchange exchange =
        new BinanceExchange() {
          @Override
          public void remoteInit() {
            // avoid remote calls for this test
          }
        };
    exchange.applySpecification(spec);
    return new BinanceBaseService(
        exchange,
        ExchangeRestProxyBuilder.forInterface(BinanceAuthenticated.class, spec).build(),
        BinanceResilience.createRegistries());
  }
}
