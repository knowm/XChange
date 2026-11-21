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
  void ltcUsdtPairMetaData() {
    InstrumentMetaData pairMetaData = metaData.getInstruments().get(CurrencyPair.LTC_USDT);
    assertThat(pairMetaData.getPriceScale()).isEqualByComparingTo(2);
    assertThat(pairMetaData.getPriceStepSize()).isEqualByComparingTo("0.01");
    assertThat(pairMetaData.getMinimumAmount()).isEqualByComparingTo("0.001");
    assertThat(pairMetaData.getMaximumAmount().longValueExact()).isEqualTo(90000);
    assertThat(pairMetaData.getCounterMinimumAmount()).isEqualByComparingTo("1");
    assertThat(pairMetaData.getAmountStepSize()).isEqualByComparingTo("0.001");
  }
}
