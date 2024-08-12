package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.gateio.GateioExchange;

class GateioMarketDataServiceIntegration {

  GateioExchange exchange = ExchangeFactory.INSTANCE.createExchange(GateioExchange.class);

  @Test
  void exchange_health() {
    ExchangeHealth actual = exchange.getMarketDataService().getExchangeHealth();
    assertThat(actual).isEqualTo(ExchangeHealth.ONLINE);
  }

}