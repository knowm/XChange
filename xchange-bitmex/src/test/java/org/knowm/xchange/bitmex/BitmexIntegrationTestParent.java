package org.knowm.xchange.bitmex;

import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;

public class BitmexIntegrationTestParent {

  protected static BitmexExchange exchange;

  @BeforeAll
  static void init() {
    exchange = ExchangeFactory.INSTANCE.createExchange(BitmexExchange.class);
  }

  @BeforeEach
  void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }
}
