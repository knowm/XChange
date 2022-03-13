package org.knowm.xchange.coincheck;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import lombok.SneakyThrows;
import org.junit.Test;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckOrderBook;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTicker;
import org.knowm.xchange.coincheck.dto.marketdata.CoincheckTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;

public class CoincheckAdapterTest {
  @Test
  public void testCreateTicker() {
    CurrencyPair pair = CurrencyPair.BTC_JPY;
    Instant time = Instant.parse("2020-01-01T00:00:00Z");
    CoincheckTicker coincheck =
        CoincheckTicker.builder()
            .last(new BigDecimal("10000.0001"))
            .bid(new BigDecimal("20000.0001"))
            .ask(new BigDecimal("30000.0001"))
            .high(new BigDecimal("40000.0001"))
            .low(new BigDecimal("50000.0001"))
            .volume(new BigDecimal("60000.0001"))
            .timestamp(time.getEpochSecond())
            .build();
    Ticker expected =
        new Ticker.Builder()
            .instrument(pair)
            .last(new BigDecimal("10000.0001"))
            .bid(new BigDecimal("20000.0001"))
            .ask(new BigDecimal("30000.0001"))
            .high(new BigDecimal("40000.0001"))
            .low(new BigDecimal("50000.0001"))
            .volume(new BigDecimal("60000.0001"))
            .timestamp(new Date(time.toEpochMilli()))
            .build();
    assertThat(CoincheckAdapter.createTicker(pair, coincheck)).isEqualTo(expected);
  }

  @Test
  public void testCreateOrderBook() {
    OrderBook book =
        CoincheckAdapter.createOrderBook(
            CurrencyPair.BTC_JPY,
            CoincheckOrderBook.builder()
                .asks(singletonList(asList(new BigDecimal("80000"), new BigDecimal("1.0"))))
                .bids(singletonList(asList(new BigDecimal("70000"), new BigDecimal("2.0"))))
                .build());

    assertThat(book.getTimeStamp()).isNull();
    assertThat(book.getAsks().size()).isEqualTo(1);
    assertThat(book.getBids().size()).isEqualTo(1);

    assertThat(book.getAsks().get(0).getTimestamp()).isNull();
    assertThat(book.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("80000"));
    assertThat(book.getAsks().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("1.0"));

    assertThat(book.getBids().get(0).getTimestamp()).isNull();
    assertThat(book.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("70000"));
    assertThat(book.getBids().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("2.0"));
  }

  @Test
  public void testCreateSortedOrderBook() {
    // Tests that the adapter creates a sorted order book.
    // Or rather, tests that the result from Coincheck is sorted as expected.
    CoincheckOrderBook orderBook =
        CoincheckTestUtil.load(
            "dto/marketdata/example-order-books-data.json", CoincheckOrderBook.class);
    OrderBook original = CoincheckAdapter.createOrderBook(CurrencyPair.BTC_JPY, orderBook);
    OrderBook sorted = new OrderBook(null, original.getAsks(), original.getBids(), true);
    assertThat(sorted).isEqualTo(original);
  }

  @Test
  @SneakyThrows
  public void testCreateTrade() {
    CoincheckTrade coincheck =
        CoincheckTrade.builder()
            .id(211288499)
            .amount(new BigDecimal("0.015"))
            .rate(new BigDecimal("5053021.0"))
            .pair("btc_jpy")
            .orderType("buy")
            .createdAt(new Date(Instant.parse("2022-02-07T16:01:32.000Z").toEpochMilli()))
            .build();
    Trade expected =
        new Trade.Builder()
            .id("211288499")
            .originalAmount(new BigDecimal("0.015"))
            .price(new BigDecimal("5053021.0"))
            .instrument(CurrencyPair.BTC_JPY)
            .type(Order.OrderType.BID)
            .timestamp(new Date(Instant.parse("2022-02-07T16:01:32.000Z").toEpochMilli()))
            .build();
    assertThat(CoincheckAdapter.createTrade(coincheck)).isEqualTo(expected);
  }
}
