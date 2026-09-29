package org.knowm.xchange.binance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

class ExchangeMetaDataIntegration extends BinanceExchangeIntegration {

  @Test
  void valid_instrumentsMetaData() {
    Map<Instrument, InstrumentMetaData> instruments =
        exchange.getExchangeMetaData().getInstruments();
    assertThat(instruments.values())
        .allSatisfy(
            instrumentMetaData -> {
              assertThat(instrumentMetaData.getPriceScale()).isNotNull();
              assertThat(instrumentMetaData.getAmountStepSize()).isNotNull();

              assertThat(instrumentMetaData.getMinimumAmount())
                  .isLessThan(instrumentMetaData.getMaximumAmount());
            });
  }

  @Test
  void valid_symbol_mapping() {
    assertThat(BinanceAdapters.toCurrencyPair("BTCUSDT")).isEqualTo(CurrencyPair.BTC_USDT);
  }
}
