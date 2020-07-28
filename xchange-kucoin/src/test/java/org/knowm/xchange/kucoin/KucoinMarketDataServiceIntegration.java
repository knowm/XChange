package org.knowm.xchange.kucoin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.knowm.xchange.kucoin.KucoinMarketDataService.PARAM_FULL_ORDERBOOK;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;

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
    exchangeMetaData.getCurrencyPairs().entrySet().forEach(pair -> System.out.println(pair));
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
  public void testOrderBookPartial() throws Exception {
    KucoinExchange exchange = exchange();
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(ETH);
    checkOrderBookIntegrity(orderBook);
    assertThat(orderBook.getAsks().size()).isLessThanOrEqualTo(100);
    assertThat(orderBook.getBids().size()).isLessThanOrEqualTo(100);
  }

  @Test
  public void testOrderBookFull() throws Exception {
    KucoinExchange exchange = exchange();
    OrderBook orderBook = exchange.getMarketDataService().getOrderBook(ETH, PARAM_FULL_ORDERBOOK);
    checkOrderBookIntegrity(orderBook);
  }

  @Test
  public void testTrades() throws Exception {
    KucoinExchange exchange = exchange();
    Trades trades = exchange.getMarketDataService().getTrades(ETH);
    assertFalse(trades.getTrades().isEmpty());
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
