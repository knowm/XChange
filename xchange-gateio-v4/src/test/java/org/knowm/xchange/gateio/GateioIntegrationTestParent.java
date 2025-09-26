package org.knowm.xchange.gateio;

import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;

public class GateioIntegrationTestParent {

  protected static GateioExchange exchange;

  @BeforeAll
  static void init() {
    if (exchange == null) {
      exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class);
    }
  }

  @BeforeEach
  void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }
}
