package info.bitrich.xchangestream.bitstamp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.bitstamp.v2.BitstampStreamingMarketDataService;
import info.bitrich.xchangestream.bitstamp.v2.BitstampStreamingService;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BitstampStreamingMarketDataServiceV2Test
    extends BitstampStreamingMarketDataServiceBaseTest {
  @Mock private BitstampStreamingService streamingService;
  private BitstampStreamingMarketDataService marketDataService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    marketDataService = new BitstampStreamingMarketDataService(streamingService);
  }

  public void testOrderbookCommon(String channelName, Supplier<TestSubscriber<OrderBook>> updater)
      throws Exception {
    // Given order book in JSON
    JsonNode orderBook = mapper.readTree(this.getClass().getResource("/order-book-v2.json"));

    when(streamingService.subscribeChannel(eq(channelName), eq("data")))
        .thenReturn(Flowable.just(orderBook));

    List<LimitOrder> bids = new ArrayList<>();
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.922"),
            CurrencyPair.BTC_EUR,
            "",
            null,
            new BigDecimal("819.9")));
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.085"),
            CurrencyPair.BTC_EUR,
            "",
            null,
            new BigDecimal("818.63")));

    List<LimitOrder> asks = new ArrayList<>();
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("2.89"),
            CurrencyPair.BTC_EUR,
            "",
            null,
            new BigDecimal("821.7")));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("5.18"),
            CurrencyPair.BTC_EUR,
            "",
            null,
            new BigDecimal("821.65")));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.035"),
            CurrencyPair.BTC_EUR,
            "",
            null,
            new BigDecimal("821.6")));

    // Call get order book Flowable
    TestSubscriber<OrderBook> test = updater.get();

    // We get order book object in correct order
    validateOrderBook(bids, asks, test);
  }

  @Test
  public void testGetDifferentialOrderBook() throws Exception {
    testOrderbookCommon(
        "diff_order_book_btceur",
        () -> marketDataService.getFullOrderBook(CurrencyPair.BTC_EUR).test());
  }

  @Test
  public void testGetOrderBook() throws Exception {
    testOrderbookCommon(
        "order_book_btceur", () -> marketDataService.getOrderBook(CurrencyPair.BTC_EUR).test());
  }

  @Test
  public void testGetTrades() throws Exception {
    // Given order book in JSON
    JsonNode trade = mapper.readTree(this.getClass().getResource("/trade-v2.json"));

    when(streamingService.subscribeChannel(eq("live_trades_btcusd"), eq("trade")))
        .thenReturn(Flowable.just(trade));

    Trade expected =
        new Trade.Builder()
            .type(Order.OrderType.ASK)
            .originalAmount(new BigDecimal("34.390000000000001"))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(new BigDecimal("914.38999999999999"))
            .timestamp(new Date(1484858423000L))
            .id("177827396")
            .build();

    // Call get order book Flowable
    TestSubscriber<Trade> test = marketDataService.getTrades(CurrencyPair.BTC_USD).test();

    // We get order book object in correct order
    validateTrades(expected, test);
  }

  @Test
  public void testGetTicker() throws Exception {
    // Given order book in JSON
    JsonNode orderBook = mapper.readTree(this.getClass().getResource("/order-book-v2.json"));

    when(streamingService.subscribeChannel(eq("order_book_btceur"), eq("data")))
        .thenReturn(Flowable.just(orderBook));

    List<Ticker> tickerList = new ArrayList<>();
    tickerList.add(
        new Ticker.Builder()
            .bidSize(new BigDecimal("0.922"))
            .instrument(CurrencyPair.BTC_EUR)
            .bid(new BigDecimal("819.9"))
            .ask(new BigDecimal("821.7"))
            .askSize(new BigDecimal("2.89"))
            .timestamp(new Date(1553720851000L))
            .build());
    tickerList.add(
        new Ticker.Builder()
            .bidSize(new BigDecimal("0.085"))
            .instrument(CurrencyPair.BTC_EUR)
            .bid(new BigDecimal("818.63"))
            .ask(new BigDecimal("821.65"))
            .askSize(new BigDecimal("5.18"))
            .timestamp(new Date(1553720851000L))
            .build());

    // Call get ticker Flowable
    TestSubscriber<Ticker> test = marketDataService.getTicker(CurrencyPair.BTC_EUR).test();

    // We get order book object in correct order
    validateTicker(tickerList, test);
  }
}
