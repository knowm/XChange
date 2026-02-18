package org.knowm.xchange.bitfinex;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

class BitfinexExchangeTest extends BitfinexExchangeWiremock {

  @Test
  void metadata_present() {
    InstrumentMetaData expectedInstrumentMetaData =
        InstrumentMetaData.builder()
            .maximumAmount(new BigDecimal("250000.0"))
            .minimumAmount(new BigDecimal("4.0"))
            .volumeScale(8)
            .priceScale(8)
            .marketOrderEnabled(true)
            .build();

    Map<Instrument, InstrumentMetaData> instruments =
        exchange.getExchangeMetaData().getInstruments();
    assertThat(instruments).hasSize(2);

    ExchangeMetaData exchangeMetadata = exchange.getExchangeMetaData();
    assertThat(exchangeMetadata.getInstruments()).hasSize(2);
    assertThat(exchangeMetadata.getInstruments().get(new CurrencyPair("ADA/USD")))
        .usingRecursiveComparison()
        .isEqualTo(expectedInstrumentMetaData);

    assertThat(exchangeMetadata.getCurrencies()).hasSize(3);
    CurrencyMetaData expectedCurrencyMetaData = new CurrencyMetaData(8, null);
    assertThat(exchangeMetadata.getCurrencies().get(Currency.USD))
        .usingRecursiveComparison()
        .isEqualTo(expectedCurrencyMetaData);
  }
}
