package org.knowm.xchange.coinex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

class CoinexExchangeIntegration {

  CoinexExchange exchange = ExchangeFactory.INSTANCE.createExchange(CoinexExchange.class);


  @Test
  void valid_metadata() {
    assertThat(exchange.getExchangeMetaData()).isNotNull();
    Map<Instrument, InstrumentMetaData> instruments = exchange.getExchangeMetaData().getInstruments();
    assertThat(instruments).containsKey(CurrencyPair.BTC_USDT);
  }


}