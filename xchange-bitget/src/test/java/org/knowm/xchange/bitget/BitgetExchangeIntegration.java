package org.knowm.xchange.bitget;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

class BitgetExchangeIntegration extends BitgetIntegrationTestParent {

  @Test
  void valid_metadata() {
    assertThat(exchange.getExchangeMetaData()).isNotNull();
    Map<Instrument, InstrumentMetaData> instruments =
        exchange.getExchangeMetaData().getInstruments();
    assertThat(instruments).containsKey(CurrencyPair.BTC_USDT);
  }
}
