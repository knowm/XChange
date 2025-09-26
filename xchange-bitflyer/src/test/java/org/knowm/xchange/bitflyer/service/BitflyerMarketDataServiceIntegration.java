package org.knowm.xchange.bitflyer.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitflyer.BitflyerIntegrationTestParent;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BitflyerMarketDataServiceIntegration extends BitflyerIntegrationTestParent {

  @Test
  void valid_single_ticker() throws IOException {
    Ticker ticker = exchange.getMarketDataService().getTicker(CurrencyPair.BTC_JPY);

    assertThat(ticker.getInstrument()).isEqualTo(CurrencyPair.BTC_JPY);
    assertThat(ticker.getLast()).isNotNull();

    if (ticker.getBid().signum() > 0 && ticker.getAsk().signum() > 0) {
      assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
    }
  }

  @Test
  void valid_orderbook() throws IOException {
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.BTC_JPY);

    assertThat(orderBook.getBids()).isNotEmpty();
    assertThat(orderBook.getAsks()).isNotEmpty();

    assertThat(orderBook.getAsks().get(0).getLimitPrice())
        .isGreaterThan(orderBook.getBids().get(0).getLimitPrice());

    assertThat(orderBook.getBids())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_JPY);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.BID);
            });

    assertThat(orderBook.getAsks())
        .allSatisfy(
            limitOrder -> {
              assertThat(limitOrder.getInstrument()).isEqualTo(CurrencyPair.BTC_JPY);
              assertThat(limitOrder.getType()).isEqualTo(OrderType.ASK);
            });
  }
}
