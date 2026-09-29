package info.bitrich.xchangestream.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Integration tests for the Crypto.com Exchange streaming connector. Requires valid sandbox API
 * keys.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class CryptoComStreamingExchangeIntegration {

  private static final Logger logger =
      LoggerFactory.getLogger(CryptoComStreamingExchangeIntegration.class);

  private static final String API_KEY = System.getenv("CRYPTOCOM_API_KEY");
  private static final String SECRET_KEY = System.getenv("CRYPTOCOM_SECRET_KEY");
  private static final CurrencyPair TEST_CURRENCY_PAIR = CurrencyPair.BTC_USDT;
  private static final int WAIT_SECONDS = 30;

  private StreamingExchange exchange;
  private StreamingMarketDataService streamingMarketDataService;
  private StreamingTradeService streamingTradeService;
  private StreamingAccountService streamingAccountService;

  @BeforeAll
  public void setUp() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CryptoComStreamingExchange.class);
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);

    exchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
    logger.info("Connecting to Crypto.com streaming exchange...");
    exchange.connect().blockingAwait();
    logger.info("Connected.");

    streamingMarketDataService = exchange.getStreamingMarketDataService();
    streamingTradeService = exchange.getStreamingTradeService();
    streamingAccountService = exchange.getStreamingAccountService();
  }

  @AfterAll
  public void tearDown() {
    if (exchange != null && exchange.isAlive()) {
      exchange.disconnect().blockingAwait();
    }
  }

  @Test
  void getTicker_shouldReceiveUpdates() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<Ticker> received = new AtomicReference<>();
    Disposable disposable =
        streamingMarketDataService
            .getTicker(TEST_CURRENCY_PAIR)
            .subscribe(
                ticker -> {
                  received.set(ticker);
                  latch.countDown();
                },
                throwable -> logger.error("Error in ticker stream", throwable));

    boolean receivedInTime = latch.await(WAIT_SECONDS, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(receivedInTime).isTrue();
    assertThat(received.get()).isNotNull();
    assertThat(received.get().getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
  }

  @Test
  void getOrderBook_shouldReceiveUpdates() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<OrderBook> received = new AtomicReference<>();
    Disposable disposable =
        streamingMarketDataService
            .getOrderBook(TEST_CURRENCY_PAIR)
            .subscribe(
                orderBook -> {
                  received.set(orderBook);
                  latch.countDown();
                },
                throwable -> logger.error("Error in order book stream", throwable));

    boolean receivedInTime = latch.await(WAIT_SECONDS, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(receivedInTime).isTrue();
    OrderBook orderBook = received.get();
    assertThat(orderBook).isNotNull();
    if (!orderBook.getBids().isEmpty()) {
      assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
    }
  }

  @Test
  void getTrades_shouldReceiveUpdates() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<Trade> received = new AtomicReference<>();
    Disposable disposable =
        streamingMarketDataService
            .getTrades(TEST_CURRENCY_PAIR)
            .subscribe(
                trade -> {
                  received.set(trade);
                  latch.countDown();
                },
                throwable -> logger.error("Error in trades stream", throwable));

    boolean receivedInTime = latch.await(WAIT_SECONDS, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(receivedInTime).isTrue();
    assertThat(received.get()).isNotNull();
    assertThat(received.get().getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
  }

  @Test
  void getBalanceChanges_shouldReceiveUpdates() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);
    AtomicReference<Balance> received = new AtomicReference<>();
    Disposable disposable =
        streamingAccountService
            .getBalanceChanges(Currency.USDT)
            .subscribe(
                balance -> {
                  received.set(balance);
                  latch.countDown();
                },
                throwable -> logger.error("Error in balance stream", throwable));

    boolean receivedInTime = latch.await(WAIT_SECONDS, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(receivedInTime).isTrue();
    assertThat(received.get()).isNotNull();
  }

  @Test
  void getOrderChangesAndUserTrades_shouldReceiveUpdates() throws InterruptedException {
    CountDownLatch orderLatch = new CountDownLatch(1);
    CountDownLatch tradeLatch = new CountDownLatch(1);
    AtomicReference<Order> receivedOrder = new AtomicReference<>();
    AtomicReference<UserTrade> receivedTrade = new AtomicReference<>();

    Disposable orderDisposable =
        streamingTradeService
            .getOrderChanges(TEST_CURRENCY_PAIR)
            .subscribe(
                order -> {
                  receivedOrder.set(order);
                  orderLatch.countDown();
                },
                throwable -> logger.error("Error in order changes stream", throwable));
    Disposable tradeDisposable =
        streamingTradeService
            .getUserTrades(TEST_CURRENCY_PAIR)
            .subscribe(
                trade -> {
                  receivedTrade.set(trade);
                  tradeLatch.countDown();
                },
                throwable -> logger.error("Error in user trades stream", throwable));

    // These only fire when the account actually trades on sandbox; place an order manually via
    // the REST connector (or the exchange UI) while this test runs to exercise it.
    logger.info(
        "Listening for order changes and user trades on {} for {} seconds...",
        TEST_CURRENCY_PAIR,
        WAIT_SECONDS);
    orderLatch.await(WAIT_SECONDS, TimeUnit.SECONDS);
    tradeLatch.await(WAIT_SECONDS, TimeUnit.SECONDS);

    orderDisposable.dispose();
    tradeDisposable.dispose();

    if (receivedOrder.get() != null) {
      assertThat(receivedOrder.get().getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
    }
    if (receivedTrade.get() != null) {
      assertThat(receivedTrade.get().getInstrument()).isEqualTo(TEST_CURRENCY_PAIR);
    }
  }
}
