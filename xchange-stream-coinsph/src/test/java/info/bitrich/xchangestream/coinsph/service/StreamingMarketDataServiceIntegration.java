package info.bitrich.xchangestream.coinsph.service;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.coinsph.CoinsphStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.disposables.Disposable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Disabled("Integration tests are disabled by default. Enable for manual execution against sandbox.")
public class StreamingMarketDataServiceIntegration {
  private static final Logger LOG =
      LoggerFactory.getLogger(StreamingMarketDataServiceIntegration.class);

  private static final CurrencyPair CURRENCY_PAIR = CurrencyPair.BTC_PHP;

  private static final String SANDBOX_API_URL = "https://9001.pl-qa.coinsxyz.me";
  private static final String API_KEY = System.getenv("COINSPH_API_KEY");
  private static final String SECRET_KEY = System.getenv("COINSPH_SECRET_KEY");

  private StreamingExchange streamingExchange;
  private StreamingMarketDataService streamingMarketDataService;

  @Before
  public void setUp() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinsphStreamingExchange.class);
    exSpec.setSslUri(SANDBOX_API_URL);
    exSpec.setApiKey(API_KEY);
    exSpec.setSecretKey(SECRET_KEY);
    exSpec.setExchangeSpecificParametersItem(StreamingExchange.USE_SANDBOX, true);

    streamingExchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
    streamingMarketDataService = streamingExchange.getStreamingMarketDataService();

    // Connect to the WebSocket
    streamingExchange.connect().blockingAwait();
  }

  @After
  public void tearDown() {
    if (streamingExchange != null) {
      streamingExchange.disconnect().blockingAwait();
    }
  }

  @Test
  public void testGetOrderBook() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<OrderBook> receivedOrderBook = new AtomicReference<>();

    // Subscribe to order book updates
    Disposable disposable =
        streamingMarketDataService
            .getOrderBook(CURRENCY_PAIR)
            .subscribe(
                orderBook -> {
                  LOG.info(
                      "Received order book: {} asks, {} bids",
                      orderBook.getAsks().size(),
                      orderBook.getBids().size());
                  receivedOrderBook.set(orderBook);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in order book subscription", throwable);
                });

    // Wait for the order book update
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedOrderBook.get()).isNotNull();
    OrderBook orderBook = receivedOrderBook.get();
    if (!orderBook.getBids().isEmpty()) {
      assertThat(orderBook.getBids().get(0).getInstrument()).isEqualTo(CURRENCY_PAIR);
    } else if (!orderBook.getAsks().isEmpty()) {
      assertThat(orderBook.getAsks().get(0).getInstrument()).isEqualTo(CURRENCY_PAIR);
    }
  }

  @Test
  public void testGetTicker() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<Ticker> receivedTicker = new AtomicReference<>();

    // Subscribe to ticker updates
    Disposable disposable =
        streamingMarketDataService
            .getTicker(CURRENCY_PAIR)
            .subscribe(
                ticker -> {
                  LOG.info("Received ticker: {}", ticker);
                  receivedTicker.set(ticker);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in ticker subscription", throwable);
                });

    // Wait for the ticker update
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedTicker.get()).isNotNull();
    assertThat(receivedTicker.get().getInstrument()).isEqualTo(CURRENCY_PAIR);
  }

  @Test
  public void testGetTrades() throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<Trade> receivedTrade = new AtomicReference<>();

    // Subscribe to trade updates
    Disposable disposable =
        streamingMarketDataService
            .getTrades(CURRENCY_PAIR)
            .subscribe(
                trade -> {
                  LOG.info("Received trade: {}", trade);
                  receivedTrade.set(trade);
                  latch.countDown();
                },
                throwable -> {
                  LOG.error("Error in trades subscription", throwable);
                });

    // Wait for the trade update
    boolean received = latch.await(30, TimeUnit.SECONDS);
    disposable.dispose();

    assertThat(received).isTrue();
    assertThat(receivedTrade.get()).isNotNull();
    assertThat(receivedTrade.get().getInstrument()).isEqualTo(CURRENCY_PAIR);
  }
}
