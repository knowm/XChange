package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bybit.BybitExchangeWiremock;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BybitMarketDataServiceTest extends BybitExchangeWiremock {

  MarketDataService marketDataService = exchange.getMarketDataService();


  @Test
  void tickers() throws IOException {
    List<Ticker> actual = marketDataService.getTickers(null);

    Ticker expected = new Ticker.Builder()
        .instrument(CurrencyPair.BTC_USDT)
        .last(new BigDecimal("25741.97"))
        .ask(new BigDecimal("25741.97"))
        .bid(new BigDecimal("25741.96"))
        .high(new BigDecimal("26049.11"))
        .low(new BigDecimal("25372.77"))
        .volume(new BigDecimal("5683.211445"))
        .quoteVolume(new BigDecimal("146073704.01447217"))
        .percentageChange(new BigDecimal("-0.0003"))
        .build();

    assertThat(actual).hasSize(2);

    assertThat(actual).first().usingRecursiveComparison().isEqualTo(expected);

  }


  @Test
  void ticker() throws IOException {
    Ticker actual = marketDataService.getTicker(CurrencyPair.BTC_USDT);

    Ticker expected = new Ticker.Builder()
        .instrument(CurrencyPair.BTC_USDT)
        .last(new BigDecimal("25838"))
        .ask(new BigDecimal("25838.01"))
        .bid(new BigDecimal("25838"))
        .high(new BigDecimal("26049.11"))
        .low(new BigDecimal("25560.01"))
        .volume(new BigDecimal("5183.468529"))
        .quoteVolume(new BigDecimal("133602935.81431006"))
        .percentageChange(new BigDecimal("0.0104"))
        .build();


    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

  }


  @Test
  void order_book() throws IOException {
    OrderBook actual = marketDataService.getOrderBook(CurrencyPair.BTC_USDT, 2);


    List<LimitOrder> expectedAsks = new ArrayList<>();
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("26198.39"))
        .originalAmount(new BigDecimal("0.786996"))
        .build());
    expectedAsks.add(new LimitOrder.Builder(OrderType.ASK, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("26198.44"))
        .originalAmount(new BigDecimal("0.3"))
        .build());

    List<LimitOrder> expectedBids = new ArrayList<>();
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("26198.38"))
        .originalAmount(new BigDecimal("0.332024"))
        .build());
    expectedBids.add(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .limitPrice(new BigDecimal("26198.29"))
        .originalAmount(new BigDecimal("0.015269"))
        .build());
    Date expectedTimestamp = Date.from(Instant.ofEpochMilli(1694123074333L));

    OrderBook expected = new OrderBook(expectedTimestamp, expectedAsks, expectedBids);

    assertThat(actual)
        .usingRecursiveComparison()
        .ignoringFieldsMatchingRegexes(".*userReference")
        .isEqualTo(expected);
  }



}