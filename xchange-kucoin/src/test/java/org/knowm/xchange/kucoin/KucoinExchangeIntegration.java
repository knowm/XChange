package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

class KucoinExchangeIntegration extends KucoinIntegrationTestParent {

  @Test
  void valid_metadata() {
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();

    assertThat(exchangeMetaData).isNotNull();
    assertThat(exchangeMetaData.getInstruments()).containsKey(CurrencyPair.BTC_USDT);
    assertThat(exchangeMetaData.getCurrencies()).containsKey(Currency.BTC);
  }
}
