package org.knowm.xchange.bitmex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitmex.BitmexIntegrationTestParent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

class BitmexMarketDataServiceIntegration extends BitmexIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker).hasNoNullFieldsOrPropertiesExcept("bidSize", "askSize");

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

}