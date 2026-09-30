package org.knowm.xchange.binance.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.BinanceExchangeIntegration;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.instrument.Instrument;

class MarketDataServiceIntegration extends BinanceExchangeIntegration {

  @Test
  void exchange_health() {
    assertThat(exchange.getMarketDataService().getExchangeHealth())
        .isEqualTo(ExchangeHealth.ONLINE);
  }

  @Test
  void valid_timestamp() {

    long serverTime = exchange.getTimestampFactory().createValue();
    assertThat(serverTime).isPositive();
  }

  @Test
  void valid_all_tickers() throws Exception {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers)
        .allSatisfy(
            ticker -> {
              assertThat(ticker.getInstrument()).isNotNull();
              assertThat(ticker.getLast()).isNotNull();
              if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
                assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
              }
            });
  }

  @Test
  void valid_ticker() throws Exception {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker).hasNoNullFieldsOrProperties();
    assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
  }

  @Test
  void valid_orderBook() throws Exception {
    OrderBook orderBook =
        exchange.getMarketDataService().getOrderBook((Instrument) CurrencyPair.BTC_USDT);

    assertThat(orderBook.getAsks()).isNotEmpty();
    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getBids().get(0)).isLessThan(orderBook.getAsks().get(0));
  }
}
