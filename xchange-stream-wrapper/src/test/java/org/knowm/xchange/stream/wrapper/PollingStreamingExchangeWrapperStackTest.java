package org.knowm.xchange.stream.wrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.simulated.SimulatedExchange.ACCOUNT_FACTORY_PARAM;
import static org.knowm.xchange.simulated.SimulatedExchange.ENGINE_FACTORY_PARAM;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import ch.qos.logback.classic.Level;
import com.google.common.util.concurrent.Service;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.simulated.AccountFactory;
import org.knowm.xchange.simulated.MatchingEngineFactory;
import org.knowm.xchange.simulated.SimulatedExchange;
import org.slf4j.LoggerFactory;

@Slf4j
public class PollingStreamingExchangeWrapperStackTest {

  private Exchange exchange;
  private Service simulatedActivity;
  private PollingStreamingExchangeWrapper singleWrapped;
  private PollingStreamingExchangeWrapper doubleWrapped;

  @Before
  public void setup() {

    // Use INFO level logging for this test otherwise the logging is overwhelming
    ((ch.qos.logback.classic.Logger)
        LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME))
        .setLevel(Level.INFO);

    // Set up a simulated exchange in the background with randomised trading activity
    AccountFactory accountFactory = new AccountFactory();
    MatchingEngineFactory matchingEngineFactory = new MatchingEngineFactory(accountFactory);
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(SimulatedExchange.class);
    exchangeSpecification.setApiKey("MarketMakers");
    exchangeSpecification.setExchangeSpecificParametersItem(ENGINE_FACTORY_PARAM, matchingEngineFactory);
    exchangeSpecification.setExchangeSpecificParametersItem(ACCOUNT_FACTORY_PARAM, accountFactory);
    exchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    simulatedActivity = new SimulatedOrderBookActivity(accountFactory, matchingEngineFactory);
    simulatedActivity.startAsync();

    RateController rateController = mock(RateController.class);
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

  @After
  public void tearDown() {
    simulatedActivity.stopAsync();
  }

  @Test
  public void testPollingTickers() throws Exception {
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testStreamingTickers() throws Exception {
    withDoubleWrapped(
        () -> assertReceiving(() -> doubleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testPollingOrderBooks() throws Exception {
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testStreamingOrderBooks() throws Exception {
    withDoubleWrapped(
        () -> assertReceiving(() -> doubleWrapped.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testPollingTrades() throws Exception {
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testStreamingTrades() throws Exception {
    withDoubleWrapped(
        () -> assertReceiving(() -> doubleWrapped.getStreamingMarketDataService().getTrades(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testPollingBalances() throws Exception {
    withSingleWrapped(() -> {
      assertReceiving(() -> singleWrapped.getStreamingAccountService().getBalanceChanges(Currency.BTC));
      assertReceiving(() -> singleWrapped.getStreamingAccountService().getBalanceChanges(Currency.USD));
    });
  }

  @Test
  public void testStreamingBalances() throws Exception {
    withDoubleWrapped(() -> {
      assertReceiving(() -> doubleWrapped.getStreamingAccountService().getBalanceChanges(Currency.BTC));
      assertReceiving(() -> doubleWrapped.getStreamingAccountService().getBalanceChanges(Currency.USD));
    });
  }

  @Test
  public void testPollingOrders() throws Exception {
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingTradeService().getOrderChanges(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testStreamingOrders() throws Exception {
    withDoubleWrapped(
        () -> assertReceiving(() -> doubleWrapped.getStreamingTradeService().getOrderChanges(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testPollingUserTrades() throws Exception {
    withSingleWrapped(
        () -> assertReceiving(() -> singleWrapped.getStreamingTradeService().getUserTrades(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testStreamingUserTrades() throws Exception {
    withDoubleWrapped(
        () -> assertReceiving(() -> doubleWrapped.getStreamingTradeService().getUserTrades(CurrencyPair.BTC_USD)));
  }

  @Test
  public void testChangeSubscriptions() throws Exception {
    AtomicReference<CountDownLatch> tickers = new AtomicReference<>(new CountDownLatch(3));
    AtomicReference<CountDownLatch> orderBooks = new AtomicReference<>(new CountDownLatch(3));
    withSingleWrapped(() -> {
      Disposable d1 = singleWrapped.getStreamingMarketDataService().getTicker(CurrencyPair.BTC_USD)
          .subscribe(it -> tickers.get().countDown());
      Thread.sleep(2000);
      Disposable d2 = singleWrapped.getStreamingMarketDataService().getOrderBook(CurrencyPair.BTC_USD)
          .subscribe(it -> orderBooks.get().countDown());
      Thread.sleep(2000);
      tickers.set(new CountDownLatch(3));
      orderBooks.set(new CountDownLatch(3));
      assertThat(tickers.get().await(30, TimeUnit.SECONDS)).isTrue();
      assertThat(orderBooks.get().await(30, TimeUnit.SECONDS)).isTrue();
      SafelyDispose.of(d1, d2);
    });
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
}
