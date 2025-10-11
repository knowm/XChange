package org.knowm.xchange.bitfinex;

import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;

public class BitfinexIntegrationTestParent {

  protected static BitfinexExchange exchange;

  @BeforeAll
  static void init() {
    if (exchange == null) {
      exchange = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class);
    }
  }

  @BeforeEach
  void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }
}
