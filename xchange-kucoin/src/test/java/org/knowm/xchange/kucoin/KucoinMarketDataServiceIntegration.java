package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.knowm.xchange.kucoin.KucoinMarketDataService.PARAM_PARTIAL_SHALLOW_ORDERBOOK;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.kucoin.dto.KlineIntervalType;
import org.knowm.xchange.kucoin.dto.response.AllTickersResponse;
import org.knowm.xchange.kucoin.dto.response.AllTickersTickerResponse;
import org.knowm.xchange.kucoin.dto.response.KucoinKline;

public class KucoinMarketDataServiceIntegration {

  private final CurrencyPair ETH = CurrencyPair.ETH_BTC;

  @Test
  public void testGetPrices() throws Exception {
    KucoinExchange exchange = exchange();
    KucoinMarketDataServiceRaw kucoinMarketDataServiceRaw = exchange.getMarketDataService();
    Map<String, BigDecimal> prices = kucoinMarketDataServiceRaw.getKucoinPrices();
    assertThat(prices.get("BTC")).isNotNull();
  }

  @Test
  public void testGetMarketData() throws Exception {
    KucoinExchange exchange = exchange();
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    exchangeMetaData
        .getCurrencyPairs()
        .entrySet()
        .forEach(
            pair -> {
              assertThat(pair.getValue().getMinimumAmount()).isNotNull();
              assertThat(pair.getValue().getMaximumAmount()).isNotNull();
              assertThat(pair.getValue().getCounterMinimumAmount()).isNotNull();
              assertThat(pair.getValue().getCounterMaximumAmount()).isNotNull();
              assertThat(pair.getValue().getBaseScale()).isNotNull();
              assertThat(pair.getValue().getPriceScale()).isNotNull();
              assertThat(pair.getValue().getTradingFeeCurrency()).isNotNull();
            });
  }

  @Test
  public void testGetTicker() throws Exception {
    KucoinExchange exchange = exchange();
    Ticker ticker = exchange.getMarketDataService().getTicker(ETH);
    assertThat(ticker.getBid()).isNotNull();
    assertThat(ticker.getAsk()).isGreaterThan(ticker.getBid());
    assertThat(ticker.getLast())
        .isGreaterThanOrEqualTo(ticker.getLow())
        .isLessThanOrEqualTo(ticker.getHigh());
    assertThat(ticker.getLow()).isNotNull();
    assertThat(ticker.getHigh()).isGreaterThan(ticker.getLow());
    // assertThat(ticker.getOpen()).isNotNull(); Seems to be mostly...
    assertThat(ticker.getVolume()).isNotNull().isGreaterThanOrEqualTo(BigDecimal.ZERO);
    assertThat(ticker.getQuoteVolume()).isNotNull().isGreaterThanOrEqualTo(BigDecimal.ZERO);
    assertThat(ticker.getCurrencyPair()).isEqualTo(ETH);
    checkTimestamp(ticker.getTimestamp());
  }

  @Test
  public void testGetTickers() throws Exception {
    KucoinExchange exchange = exchange();
    AllTickersResponse allPairs = exchange.getMarketDataService().getKucoinTickers();
    // Do a minimal check to make sure we're getting multiple tickers
    assertThat(allPairs.getTicker().length).isGreaterThan(0);
    // Tease out eth ticker
    Optional<AllTickersTickerResponse> optionalPair =
        Arrays.stream(allPairs.getTicker())
            .filter(a -> a.getSymbol().equalsIgnoreCase("ETH-USDT"))
            .findFirst();
    assertThat(optionalPair).isNotEmpty();
    AllTickersTickerResponse tickerResponse = optionalPair.get();
    tickerResponse.getSymbol().equals("ETH-USDT");

    assertThat(tickerResponse.getBuy()).isNotNull();
    assertThat(tickerResponse.getHigh()).isNotNull();
    assertThat(tickerResponse.getLast()).isNotNull();
    assertThat(tickerResponse.getLow()).isNotNull();
    assertThat(tickerResponse.getHigh()).isGreaterThan(tickerResponse.getLow());
    assertThat(tickerResponse.getVol()).isNotNull().isGreaterThanOrEqualTo(BigDecimal.ZERO);
    assertThat(tickerResponse.getVolValue()).isNotNull().isGreaterThanOrEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void testOrderBookPartial() throws Exception {
    KucoinExchange exchange = exchange();
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(ETH);
    checkOrderBookIntegrity(orderBook);
    assertThat(orderBook.getAsks().size()).isLessThanOrEqualTo(100);
    assertThat(orderBook.getBids().size()).isLessThanOrEqualTo(100);
  }

  @Test
  public void testOrderBookPartialShallow() throws Exception {
    KucoinExchange exchange = exchange();
    OrderBook orderBook =
        exchange.getMarketDataService().getOrderBook(ETH, PARAM_PARTIAL_SHALLOW_ORDERBOOK);
    checkOrderBookIntegrity(orderBook);
    assertThat(orderBook.getAsks().size()).isLessThanOrEqualTo(20);
    assertThat(orderBook.getBids().size()).isLessThanOrEqualTo(20);
  }

  @Test
  public void testTrades() throws Exception {
    KucoinExchange exchange = exchange();
    Trades trades = exchange.getMarketDataService().getTrades(ETH);
    assertFalse(trades.getTrades().isEmpty());
  }

  @Test
  public void testKlines() throws Exception {
    KucoinExchange exchange = exchange();
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

  private KucoinExchange exchange() {
    return ExchangeFactory.INSTANCE.createExchange(KucoinExchange.class);
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
