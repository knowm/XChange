package info.bitrich.xchangestream.coincheck;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;

class CoincheckStreamingIntegration {
  CompositeDisposable disposables;
  CoincheckStreamingExchange exchange;
  CoincheckStreamingMarketDataService marketDataService;

  @BeforeEach
  void setup() {
    this.disposables = new CompositeDisposable();
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchange(CoincheckStreamingExchange.class)
            .getDefaultExchangeSpecification();
    this.exchange =
        (CoincheckStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    this.exchange.connect().blockingAwait();
    this.marketDataService = this.exchange.getStreamingMarketDataService();
  }

  @AfterEach
  void teardown() {
    disposables.dispose();
    exchange.disconnect();
  }

  @Test
  @Timeout(
      value = 65000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  @SneakyThrows
  void streamingTrades() {
    AtomicInteger count = new AtomicInteger();
    disposables.add(
        marketDataService.getTrades(CurrencyPair.BTC_JPY).subscribe(t -> count.incrementAndGet()));
    waitUntil(() -> count.get() > 1, Duration.ofSeconds(60));
    assertThat(count.get()).isGreaterThan(1);
  }

  @Test
  @Timeout(
      value = 15000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  @SneakyThrows
  void streamingOrderBooks() {
    AtomicInteger count = new AtomicInteger();
    disposables.add(
        marketDataService
            .getOrderBook(CurrencyPair.BTC_JPY)
            .subscribe(t -> count.incrementAndGet()));
    waitUntil(() -> count.get() > 3, Duration.ofSeconds(10));
    assertThat(count.get()).isGreaterThan(3);
  }

  @Test
  @Timeout(
      value = 15000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  @SneakyThrows
  void streamingTickers() {
    AtomicInteger count = new AtomicInteger();
    disposables.add(
        marketDataService.getTicker(CurrencyPair.BTC_JPY).subscribe(t -> count.incrementAndGet()));
    waitUntil(() -> count.get() > 3, Duration.ofSeconds(10));
    assertThat(count.get()).isGreaterThan(3);
  }

  @SneakyThrows
  private static void waitUntil(BooleanSupplier condition, Duration timeout) {
    Instant start = Instant.now();
    while (!condition.getAsBoolean()
        && Duration.between(start, Instant.now()).compareTo(timeout) < 0) {
      Thread.sleep(100);
    }
  }
}
