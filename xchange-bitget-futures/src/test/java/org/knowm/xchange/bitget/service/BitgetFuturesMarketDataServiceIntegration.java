package org.knowm.xchange.bitget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.io.IOException;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitget.BitgetFuturesIntegrationTestParent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.InstrumentNotValidException;
import org.knowm.xchange.instrument.Instrument;

class BitgetFuturesMarketDataServiceIntegration extends BitgetFuturesIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_instruments() throws IOException {
    List<Instrument> instruments =
        ((BitgetFuturesMarketDataService) exchange.getMarketDataService()).getInstruments();

    assertThat(instruments).isNotEmpty();
    assertThat(instruments.stream().distinct().count()).isEqualTo(instruments.size());
  }

  @Test
  void check_exceptions() {
    assertThatExceptionOfType(InstrumentNotValidException.class)
        .isThrownBy(
            () ->
                exchange
                    .getMarketDataService()
                    .getTicker(new CurrencyPair("NONEXISTING/NONEXISTING")));
  }

  @Test
  void valid_tickers() throws IOException {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers)
        .allSatisfy(
            ticker -> {
              assertThat(ticker.getInstrument()).isNotNull();
              assertThat(ticker.getInstrument()).isInstanceOf(FuturesContract.class);
              FuturesContract futuresContract = (FuturesContract) ticker.getInstrument();
              assertThat(futuresContract.getCurrencyPair()).isNotNull();

              assertThat(ticker.getLast()).isNotNull();

              if (ObjectUtils.allNotNull(ticker.getBid(), ticker.getAsk())
                  && ticker.getBid().signum() > 0
                  && ticker.getAsk().signum() > 0) {
                assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
              }
            });
  }
}
