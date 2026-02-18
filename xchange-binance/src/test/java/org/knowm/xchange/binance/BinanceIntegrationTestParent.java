package org.knowm.xchange.binance;

import static org.assertj.core.api.Assumptions.assumeThat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;

public class BinanceIntegrationTestParent {

  protected static BinanceExchange exchange;

  @BeforeClass
  public static void init() {
    if (exchange == null) {
      exchange = ExchangeFactory.INSTANCE.createExchange(BinanceExchange.class);
    }
  }

  @Before
  public void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }
}
