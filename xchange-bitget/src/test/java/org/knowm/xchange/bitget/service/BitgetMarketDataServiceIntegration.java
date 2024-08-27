package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assumptions.assumeThat;

import java.io.IOException;
import java.time.Instant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.dto.meta.ExchangeHealth;

class BitgetMarketDataServiceIntegration {

  BitgetExchange exchange = ExchangeFactory.INSTANCE.createExchange(BitgetExchange.class);


  @BeforeEach
  void exchange_online() {
      // skip if offline
      assumeThat(exchange.getMarketDataService().getExchangeHealth()).isEqualTo(ExchangeHealth.ONLINE);
  }


  @Test
  void server_time() throws IOException {
    Instant a = ((BitgetMarketDataServiceRaw) exchange.getMarketDataService()).getBitgetServerTime().getServerTime();
    System.out.println(a);
  }

}