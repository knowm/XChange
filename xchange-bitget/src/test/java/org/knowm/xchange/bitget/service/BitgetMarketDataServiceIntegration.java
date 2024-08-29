package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetIntegrationTestParent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.InstrumentNotValidException;

class BitgetMarketDataServiceIntegration extends BitgetIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }

  }


  @Test
  void check_exceptions() {
    assertThatExceptionOfType(InstrumentNotValidException.class)
        .isThrownBy(() ->
            exchange.getMarketDataService().getTicker(new CurrencyPair("NONEXISTING/NONEXISTING")));

  }


  @Test
  void valid_tickers() throws IOException {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers).allSatisfy(ticker -> {
      assertThat(ticker.getInstrument()).isNotNull();
      assertThat(ticker.getLast()).isNotNull();

      if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
        assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
      }
    });
  }


}