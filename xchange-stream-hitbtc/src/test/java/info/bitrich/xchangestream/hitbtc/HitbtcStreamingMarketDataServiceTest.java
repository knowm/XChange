package info.bitrich.xchangestream.hitbtc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HitbtcStreamingMarketDataServiceTest {

  @Mock private HitbtcStreamingService streamingService;
  private HitbtcStreamingMarketDataService marketDataService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() {
    marketDataService = new HitbtcStreamingMarketDataService(streamingService);
  }

  @Test
  public void testOrderbookCommon() throws Exception {

    // Read order book in JSON
    String orderBook =
        new String(
            Files.readAllBytes(
                Paths.get(
                    getClass().getResource("/example/notificationSnapshotOrderBook.json").toURI())),
            StandardCharsets.UTF_8);

    when(streamingService.subscribeChannel(eq("orderbook-BTCEUR")))
        .thenReturn(Flowable.just(objectMapper.readTree(orderBook)));

    List<LimitOrder> bids = new ArrayList<>();
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.500"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("0.054558")));
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("0.076"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("0.054557")));
    bids.add(
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal("7.725"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("0.054524")));

    List<LimitOrder> asks = new ArrayList<>();
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("0.245"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("0.054588")));
    asks.add(
        new LimitOrder(
            Order.OrderType.ASK,
            new BigDecimal("2.784"),
            CurrencyPair.BTC_EUR,
            null,
            null,
            new BigDecimal("0.054591")));

    // Call get order book Flowable
    TestSubscriber<OrderBook> test = marketDataService.getOrderBook(CurrencyPair.BTC_EUR).test();

    // We get order book object in correct order
    test.assertValue(
        orderBook1 -> {
          assertThat(orderBook1.getAsks()).as("Asks").isEqualTo(asks);
          assertThat(orderBook1.getBids()).as("Bids").isEqualTo(bids);
          return true;
        });
  }

  @Test
  public void testGetTrades() throws Exception {
    // Read trades in JSON
    String trades =
        new String(
            Files.readAllBytes(
                Paths.get(
                    getClass().getResource("/example/notificationSnapshotTrades.json").toURI())),
            StandardCharsets.UTF_8);

    when(streamingService.subscribeChannel(eq("trades-BTCUSD")))
        .thenReturn(Flowable.just(objectMapper.readTree(trades)));

    Trade expected1 =
        new Trade.Builder()
            .type(Order.OrderType.BID)
            .originalAmount(new BigDecimal("0.057"))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(new BigDecimal("0.054656"))
            .timestamp(new Date(1508430822821L))
            .id("54469456")
            .build();

    Trade expected2 =
        new Trade.Builder()
            .type(Order.OrderType.BID)
            .originalAmount(new BigDecimal("0.092"))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(new BigDecimal("0.054656"))
            .timestamp(new Date(1508430828754L))
            .id("54469497")
            .build();

    Trade expected3 =
        new Trade.Builder()
            .type(Order.OrderType.BID)
            .originalAmount(new BigDecimal("0.002"))
            .currencyPair(CurrencyPair.BTC_USD)
            .price(new BigDecimal("0.054669"))
            .timestamp(new Date(1508430853288L))
            .id("54469697")
            .build();

    // Call get trades Flowable
    TestSubscriber<Trade> test = marketDataService.getTrades(CurrencyPair.BTC_USD).test();

    test.assertValues(expected1, expected2, expected3);
    validateTrade(0, test, expected1);
    validateTrade(1, test, expected2);
    validateTrade(2, test, expected3);
  }

  private void validateTrade(int index, TestSubscriber<Trade> test, Trade expected) {
    test.assertValueAt(
        index,
        trade -> {
          assertThat(trade.getPrice()).isEqualTo(expected.getPrice());
          assertThat(trade.getType()).isEqualTo(expected.getType());
          assertThat(trade.getOriginalAmount()).isEqualTo(expected.getOriginalAmount());
          assertThat(trade.getCurrencyPair()).isEqualTo(expected.getCurrencyPair());
          assertThat(trade.getTimestamp()).isEqualTo(expected.getTimestamp());
          return true;
        });
  }

  @Test
  public void testGetTicker() throws Exception {
    // Read ticker in JSON
    String tickerString =
        new String(
            Files.readAllBytes(
                Paths.get(getClass().getResource("/example/notificationTicker.json").toURI())),
            StandardCharsets.UTF_8);

    when(streamingService.subscribeChannel(eq("ticker-BTCUSD")))
        .thenReturn(Flowable.just(objectMapper.readTree(tickerString)));

    Ticker expected =
        new Ticker.Builder()
            .currencyPair(CurrencyPair.BTC_USD)
            .last(new BigDecimal("0.054463"))
            .bid(new BigDecimal("0.054463"))
            .ask(new BigDecimal("0.054464"))
            .high(new BigDecimal("0.057559"))
            .low(new BigDecimal("0.053615"))
            .volume(new BigDecimal("33068.346"))
            .timestamp(new Date(1508427944941L))
            .build();

    // Call get ticker Flowable
    TestSubscriber<Ticker> test = marketDataService.getTicker(CurrencyPair.BTC_USD).test();

    test.assertValue(
        ticker -> {
          assertThat(ticker.getAsk()).isEqualTo(expected.getAsk());
          assertThat(ticker.getBid()).isEqualTo(expected.getBid());
          assertThat(ticker.getHigh()).isEqualTo(expected.getHigh());
          assertThat(ticker.getLow()).isEqualTo(expected.getLow());
          assertThat(ticker.getLast()).isEqualTo(expected.getLast());
          assertThat(ticker.getVolume()).isEqualTo(expected.getVolume());
          assertThat(ticker.getTimestamp()).isEqualTo(expected.getTimestamp());
          return true;
        });
  }
}
