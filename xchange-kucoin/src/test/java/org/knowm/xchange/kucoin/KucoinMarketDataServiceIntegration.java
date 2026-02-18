package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.knowm.xchange.kucoin.KucoinMarketDataService.PARAM_PARTIAL_SHALLOW_ORDERBOOK;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.kucoin.dto.KlineIntervalType;
import org.knowm.xchange.kucoin.dto.response.KucoinCurrencyResponseV3;
import org.knowm.xchange.kucoin.dto.response.KucoinKline;

public class KucoinMarketDataServiceIntegration extends KucoinIntegrationTestParent {

  @Test
  public void valid_currency_infos() throws Exception {
    KucoinMarketDataService kucoinMarketDataService = exchange.getMarketDataService();
    List<KucoinCurrencyResponseV3> currencyInfos = kucoinMarketDataService.getAllKucoinCurrencies();
    assertThat(
            currencyInfos.stream()
                .filter(currencyInfo -> currencyInfo.getCurrency().equals(Currency.USDT))
                .findFirst())
        .isNotEmpty();
  }

  @Test
  public void testGetPrices() throws Exception {
    KucoinMarketDataServiceRaw kucoinMarketDataServiceRaw = exchange.getMarketDataService();
    Map<String, BigDecimal> prices = kucoinMarketDataServiceRaw.getKucoinPrices();
    assertThat(prices.get("BTC")).isNotNull();
  }

  @Test
  public void testGetMarketData() throws Exception {
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    exchangeMetaData
        .getInstruments()
        .entrySet()
        .forEach(
            pair -> {
              assertThat(pair.getValue().getMinimumAmount()).isNotNull();
              assertThat(pair.getValue().getMaximumAmount()).isNotNull();
              assertThat(pair.getValue().getCounterMinimumAmount()).isNotNull();
              assertThat(pair.getValue().getCounterMaximumAmount()).isNotNull();
              assertThat(pair.getValue().getVolumeScale()).isNotNull();
              assertThat(pair.getValue().getPriceScale()).isNotNull();
              assertThat(pair.getValue().getTradingFeeCurrency()).isNotNull();
            });
  }

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
  void valid_tickers() throws IOException {
    List<Ticker> tickers = exchange.getMarketDataService().getTickers(null);
    assertThat(tickers).isNotEmpty();

    assertThat(tickers)
        .allSatisfy(
            ticker -> {
              assertThat(ticker.getInstrument()).isNotNull();

              if (ticker.getBid() != null
                  && ticker.getBid().signum() > 0
                  && ticker.getAsk() != null
                  && ticker.getAsk().signum() > 0) {
                assertThat(ticker.getBid()).isLessThan(ticker.getAsk());
              }
            });
  }

  @Test
  public void testOrderBookPartial() throws Exception {
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_BTC);
    checkOrderBookIntegrity(orderBook);
    assertThat(orderBook.getAsks().size()).isLessThanOrEqualTo(100);
    assertThat(orderBook.getBids().size()).isLessThanOrEqualTo(100);
  }

  @Test
  public void testOrderBookPartialShallow() throws Exception {
    OrderBook orderBook =
        exchange
            .getMarketDataService()
            .getOrderBook(CurrencyPair.ETH_BTC, PARAM_PARTIAL_SHALLOW_ORDERBOOK);
    checkOrderBookIntegrity(orderBook);
    assertThat(orderBook.getAsks().size()).isLessThanOrEqualTo(20);
    assertThat(orderBook.getBids().size()).isLessThanOrEqualTo(20);
  }

  @Test
  public void testTrades() throws Exception {
    Trades trades = exchange.getMarketDataService().getTrades(CurrencyPair.ETH_BTC);
    assertFalse(trades.getTrades().isEmpty());
  }

  @Test
  public void testKlines() throws Exception {
    // Taken from the api docs page: GET
    // /api/v1/market/candles?type=1min&symbol=BTC-USDT&startAt=1566703297&endAt=1566789757
    List<KucoinKline> klines =
        exchange
            .getMarketDataService()
            .getKucoinKlines(
                CurrencyPair.BTC_USDT, 1566703297L, 1566789757L, KlineIntervalType.min1);
    assertFalse(klines.isEmpty());
    assertThat(klines.size()).isEqualTo(1441);

    // Since this is a fixed range in time, we know length, first, and last of collection
    KucoinKline first = klines.get(0);
    assertThat(first.getPair()).isEqualByComparingTo(CurrencyPair.BTC_USDT);
    assertThat(first.getIntervalType()).isEqualByComparingTo(KlineIntervalType.min1);
    assertThat(first.getTime()).isEqualTo(1566789720L);
    assertThat(first.getOpen()).isEqualTo(BigDecimal.valueOf(10411.5));
    assertThat(first.getHigh()).isEqualTo(BigDecimal.valueOf(10411.5));
    assertThat(first.getLow()).isEqualTo(BigDecimal.valueOf(10396.3));
    assertThat(first.getClose()).isEqualTo(BigDecimal.valueOf(10401.9));
    assertThat(first.getVolume()).isEqualTo(BigDecimal.valueOf(29.11357276));
    assertThat(first.getAmount()).isEqualTo(BigDecimal.valueOf(302889.301529914));

    KucoinKline last = klines.get(klines.size() - 1);
    assertThat(last.getPair()).isEqualByComparingTo(CurrencyPair.BTC_USDT);
    assertThat(last.getIntervalType()).isEqualByComparingTo(KlineIntervalType.min1);
    assertThat(last.getTime()).isEqualTo(1566703320L);
    assertThat(last.getOpen()).isEqualTo(BigDecimal.valueOf(10089));
    assertThat(last.getHigh()).isEqualTo(BigDecimal.valueOf(10092.1));
    assertThat(last.getLow()).isEqualTo(BigDecimal.valueOf(10087.7));
    assertThat(last.getClose()).isEqualTo(BigDecimal.valueOf(10088.7));
    assertThat(last.getVolume()).isEqualTo(BigDecimal.valueOf(5.12048315));
    assertThat(last.getAmount()).isEqualTo(BigDecimal.valueOf(51658.509394017));
  }

  private void checkTimestamp(Date date) {
    assertThat(
        Math.abs(
                LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC)
                    .until(LocalDateTime.now(), ChronoUnit.MINUTES))
            < 5);
  }

  private void checkOrderBookIntegrity(OrderBook orderBook) {
    BigDecimal previousPrice = new BigDecimal(1000000000000000000L);
    for (LimitOrder o : orderBook.getBids()) {
      assertThat(o.getLimitPrice()).isLessThan(previousPrice);
      previousPrice = o.getLimitPrice();
      assertNotEquals(0, o.getOriginalAmount().compareTo(BigDecimal.ZERO));
    }
    previousPrice =
        orderBook.getBids().isEmpty()
            ? BigDecimal.ZERO
            : orderBook.getBids().get(0).getLimitPrice();
    for (LimitOrder o : orderBook.getAsks()) {
      assertThat(o.getLimitPrice()).isGreaterThan(previousPrice);
      previousPrice = o.getLimitPrice();
      assertNotEquals(0, o.getOriginalAmount().compareTo(BigDecimal.ZERO));
    }
    checkTimestamp(orderBook.getTimeStamp());
  }
}
