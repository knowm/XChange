package org.knowm.xchange.stream.wrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.InvocationResult;

@Slf4j
public class PollingStreamingExchangeWrapperMockTest {

  private Exchange exchange;
  private PollingStreamingExchangeWrapper singleWrapped;
  private PollingStreamingExchangeWrapper doubleWrapped;
  private RateController rateController;

  @Before
  public void setup() {

    // Use INFO level logging for this test otherwise the logging is overwhelming
    ((ch.qos.logback.classic.Logger)
        LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME))
        .setLevel(Level.INFO);

    exchange = mock(Exchange.class, RETURNS_DEEP_STUBS);

    rateController = mock(RateController.class);
    doAnswer(inv -> {
      Thread.sleep(200);
      return null;
    }).when(rateController).acquire();

    // Wrap the exchange once (to test ability to poll)
    singleWrapped = new PollingStreamingExchangeWrapper(
        "simulated",
        exchange,
        rateController,
        1000, true);

    // Wrap that again to test that we can connect streams too
    doubleWrapped = new PollingStreamingExchangeWrapper(
        "wrapped",
        singleWrapped,
        rateController,
        1000, true);
  }

  @Test
  public void testPollExceptionUnknown() throws Exception {
    failThreeTimes(() -> new RuntimeException("DELIBERATE"));
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)));
    verify(rateController, times(3)).throttle();
    verify(rateController, times(0)).backoff();
    verify(rateController, times(0)).pause();
  }

  @Test
  public void testPollExceptionHttpStatus() throws Exception {
    failThreeTimes(() -> new HttpStatusIOException("DELIBERATE", new InvocationResult("Foo", 502)));
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)));
    verify(rateController, times(3)).throttle();
    verify(rateController, times(0)).backoff();
    verify(rateController, times(0)).pause();
  }

  @Test
  public void testRateLimitExceeded() throws Exception {
    failThreeTimes(() -> new RateLimitExceededException("DELIBERATE"));
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)));
    verify(rateController, times(0)).throttle();
    verify(rateController, times(3)).backoff();
    verify(rateController, times(3)).pause();
  }

  @Test
  public void testUnsupportedOperation() throws Exception {
    AtomicInteger attemptCount = new AtomicInteger(0);
    when(exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USD)).then(inv -> {
      if (attemptCount.getAndIncrement() > 2) {
        return new Ticker.Builder().currencyPair(CurrencyPair.BTC_USD).build();
      } else {
        throw new UnsupportedOperationException("DELIBERATE");
      }
    });
    withSingleWrapped(() -> assertThatThrownBy(
        () -> singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD).blockingSubscribe())
        .isInstanceOf(UnsupportedOperationException.class));
    verify(rateController, times(0)).throttle();
    verify(rateController, times(0)).backoff();
    verify(rateController, times(0)).pause();
  }

  private void withSingleWrapped(Action action) throws Exception {
    assertThat(singleWrapped.connect().blockingAwait(5, TimeUnit.SECONDS)).isTrue();
    try {
      action.run();
    } finally {
      assertThat(singleWrapped.disconnect().blockingAwait(5, TimeUnit.SECONDS)).isTrue();
    }
  }

  private void withDoubleWrapped(Action action) throws Exception {
    assertThat(doubleWrapped.connect().blockingAwait(5, TimeUnit.SECONDS)).isTrue();
    try {
      action.run();
    } finally {
      assertThat(doubleWrapped.disconnect().blockingAwait(5, TimeUnit.SECONDS)).isTrue();
    }
  }

  private <T> void assertReceiving(Supplier<Observable<T>> rx) throws InterruptedException {
    CountDownLatch gotTwo1 = new CountDownLatch(2);
    CountDownLatch gotTwo2 = new CountDownLatch(2);
    Disposable disposable1 = rx.get().subscribe(t -> {
      log.info("Got (1): {}", t);
      gotTwo1.countDown();
    });
    Disposable disposable2 = rx.get().subscribe(t -> {
      log.info("Got (2): {}", t);
      gotTwo2.countDown();
    });
    try {
      assertThat(gotTwo1.await(30, TimeUnit.SECONDS)).isTrue();
      assertThat(gotTwo2.await(30, TimeUnit.SECONDS)).isTrue();
    } finally {
      SafelyDispose.of(disposable1, disposable2);
    }
  }

  private void failThreeTimes(Supplier<Exception> exceptionSupplier) throws IOException {
    AtomicInteger attemptCount = new AtomicInteger(0);
    when(exchange.getMarketDataService().getTicker(CurrencyPair.BTC_USD)).then(inv -> {
      if (attemptCount.getAndIncrement() > 2) {
        return new Ticker.Builder().currencyPair(CurrencyPair.BTC_USD).build();
      } else {
        throw exceptionSupplier.get();
      }
    });
  }
}
