package info.bitrich.xchangestream.bitstamp;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import info.bitrich.xchangestream.service.pusher.PusherStreamingService;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BitstampStreamingMarketDataServiceTest
    extends BitstampStreamingMarketDataServiceBaseTest {
  @Mock private PusherStreamingService streamingService;
  private BitstampStreamingMarketDataService marketDataService;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    marketDataService = new BitstampStreamingMarketDataService(streamingService);
  }

  public void testOrderbookCommon(String channelName, Supplier<TestObserver<OrderBook>> updater)
      throws Exception {
    // Given order book in JSON
    String orderBook =
        new String(
            Files.readAllBytes(
                Paths.get(ClassLoader.getSystemResource("order-book.json").toURI())));

    when(streamingService.subscribeChannel(eq(channelName), eq("data")))
        .thenReturn(Observable.just(orderBook));

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

    // Call get order book observable
    TestObserver<OrderBook> test = updater.get();

    // We get order book object in correct order
    validateOrderBook(bids, asks, test);
  }

  @Test
  public void testGetDifferentialOrderBook() throws Exception {
    testOrderbookCommon(
        "diff_order_book_btceur",
        () -> marketDataService.getDifferentialOrderBook(CurrencyPair.BTC_EUR).test());
  }

  @Test
  public void testGetOrderBook() throws Exception {
    testOrderbookCommon(
        "order_book_btceur", () -> marketDataService.getOrderBook(CurrencyPair.BTC_EUR).test());
  }

  @Test
  public void testGetTrades() throws Exception {
    // Given order book in JSON
    String trade =
        new String(
            Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("trade.json").toURI())));

    when(streamingService.subscribeChannel(eq("live_trades"), eq("trade")))
        .thenReturn(Observable.just(trade));

    Trade expected =
        new Trade.Builder()
            .type(Order.OrderType.ASK)
            .originalAmount(new BigDecimal("34.390000000000001"))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(new BigDecimal("914.38999999999999"))
            .timestamp(new Date(1484858423000L))
            .id("177827396")
            .build();

    // Call get order book observable
    TestObserver<Trade> test = marketDataService.getTrades(CurrencyPair.BTC_USD).test();

    // We get order book object in correct order
    validateTrades(expected, test);
  }

  @Test(expected = NotAvailableFromExchangeException.class)
  public void testGetTicker() {
    marketDataService.getTicker(CurrencyPair.BTC_EUR).test();
  }
}
