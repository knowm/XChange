package org.knowm.xchange.binance.us;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

class ExchangeMetaDataIntegration extends BinanceUsExchangeIntegration {

  static ExchangeMetaData metaData;

  @BeforeAll
  static void fetchMetaData() throws Exception {
    createExchange();
    metaData = exchange.getExchangeMetaData();
  }

  @Test
  void ethBtcPairMetaData() {
    InstrumentMetaData pairMetaData = metaData.getInstruments().get(CurrencyPair.ETH_BTC);
    assertThat(pairMetaData.getPriceScale()).isEqualByComparingTo(5);
    assertThat(pairMetaData.getPriceStepSize()).isEqualByComparingTo("0.00001");
    assertThat(pairMetaData.getMinimumAmount()).isEqualByComparingTo("0.0001");
    assertThat(pairMetaData.getMaximumAmount().longValueExact()).isEqualTo(100000);
    assertThat(pairMetaData.getCounterMinimumAmount()).isEqualByComparingTo("0.0001");
    assertThat(pairMetaData.getAmountStepSize()).isEqualByComparingTo("0.0001");
  }

  @Test
  void ltcBtcPairMetaData() {
    InstrumentMetaData pairMetaData = metaData.getInstruments().get(new CurrencyPair("LTC/BTC"));
    assertThat(pairMetaData.getPriceScale()).isEqualByComparingTo(6);
    assertThat(pairMetaData.getPriceStepSize()).isEqualByComparingTo("0.000001");
    assertThat(pairMetaData.getMinimumAmount()).isEqualByComparingTo("0.001");
    assertThat(pairMetaData.getMaximumAmount().longValueExact()).isEqualTo(100000);
    assertThat(pairMetaData.getCounterMinimumAmount()).isEqualByComparingTo("0.0001");
    assertThat(pairMetaData.getAmountStepSize()).isEqualByComparingTo("0.001");
  }
}
