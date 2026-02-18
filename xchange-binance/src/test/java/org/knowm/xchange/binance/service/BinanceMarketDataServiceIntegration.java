package org.knowm.xchange.binance.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceIntegrationTestParent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BinanceMarketDataServiceIntegration extends BinanceIntegrationTestParent {

  @Test
  public void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USDT);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  public void valid_tickers() throws IOException {
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
  public void valid_orderbook() throws IOException {
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.BTC_USDT);

    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isGreaterThan(orderBook.getBids().get(0).getLimitPrice());

    assertThat(orderBook.getBids())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(Order.OrderType.BID);
            });

    assertThat(orderBook.getAsks())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
              assertThat(limitOrder.getType()).isEqualTo(Order.OrderType.ASK);
            });
  }
}
