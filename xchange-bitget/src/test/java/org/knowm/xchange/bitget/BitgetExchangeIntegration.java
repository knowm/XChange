package org.knowm.xchange.bitget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.knowm.xchange.dto.meta.ExchangeHealth.ONLINE;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

class BitgetExchangeIntegration {

  BitgetExchange exchange = ExchangeFactory.INSTANCE.createExchange(BitgetExchange.class);

  @BeforeEach
  void exchange_online() {
    // skip if offline
    assumeThat(exchange.getMarketDataService().getExchangeHealth()).isEqualTo(ONLINE);
  }


  @Test
  void valid_metadata() {
    assertThat(exchange.getExchangeMetaData()).isNotNull();
    Map<Instrument, InstrumentMetaData> instruments = exchange.getExchangeMetaData().getInstruments();
    assertThat(instruments).containsKey(CurrencyPair.BTC_USDT);
  }



}