package org.knowm.xchange.kraken;

import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;

public class KrakenIntegrationTestParent {

  protected static KrakenExchange exchange;

  @BeforeAll
  public static void init() {
    if (exchange == null) {
      exchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class);
    }
  }

  @BeforeEach
  public void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }
}
