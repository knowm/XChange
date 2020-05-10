package org.knowm.xchange.stream.wrapper;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingAccountService;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.exception.NotConnectedException;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.stream.wrapper.Loop.BalanceEvent;
import org.knowm.xchange.stream.wrapper.Loop.OrderBookEvent;
import org.knowm.xchange.stream.wrapper.Loop.OrderEvent;
import org.knowm.xchange.stream.wrapper.Loop.TickerEvent;
import org.knowm.xchange.stream.wrapper.Loop.TradeEvent;
import org.knowm.xchange.stream.wrapper.Loop.UserTradeEvent;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * A wrapper for {@link Exchange} instances that performs several roles:
 *
 * <ul>
 *   <li>Adds {@link StreamingExchange} capability to non-streaming {@link Exchange} implementations, allowing
 *   them to be used in a non-blocking way like any other {@link StreamingExchange}. This avoids the complexity
 *   of having to mix-and-match client code when using a variety of different exchanges in the same application.
 *   Just use a {@link PollingStreamingExchangeWrapper} for every exchange and treat them all as RX sources.</li>
 *   <li>In doing so, migrates all blocking code into dedicated threads, allowing you to ensure that computation
 *   threads remain fully active and can therefore be predictably sized (e.g. to the number of cores).</li>
 *   <li>Avoids the need for client code to implement any resilience handling. Most common temporary exchange
 *   issues are automatically detected and logged, and exponential backoff is used to stop spamming failing
 *   endpoints. Dramatically reduces production failures requiring intervention.</li>
 *   <li>Allows graceful fallback where exchanges partially implement the {@link StreamingExchange} API,
 *   detecting this and using polling instead for only those endpoints.</li>
 *   <li>Attempts to commonise variations in exchange implementations so that every exchange can be addressed
 *   using the same API. Mostly these will be migrated into the individual exchanges over time to ensure consistency,
 *   but this works as a temporary stopgap while the difficulties of changing existing exchange implementations
 *   can be discussed.  However, in some cases, such variations are unlikely to ever be resolved (such as Binance
 *   requiring all subscriptions to be made up-front via a {@link ProductSubscription}) and this removes the need
 *   to worry about that: the websocket is automatically disconnected and reconnected transparently without
 *   requiring {@link Observable}s to be recycled.</li>
 * </ul>
 *
 * <p>Usage:</p>
 *
 * <blockquote><pre>// Create the wrapper
 * StreamingExchange exchange = new PollingStreamingExchangeWrapper("binance", binanceExchange,
 * rateController, 1000, true);
 * // Start the background worker
 * exchange.connect().blockingAwait();
 * // Use like any normal StreamingExchange
 * // Shut down
 * exchange.disconnect().blockingAwait();</pre></blockquote>
 */
@Slf4j
public final class PollingStreamingExchangeWrapper implements StreamingExchange {

  private final Exchange exchange;
  private final Supplier<Connection> connectionFactory;

  private volatile boolean alive;
  private Connection connection;

  public PollingStreamingExchangeWrapper(String exchangeName,
      Exchange exchange,
      RateController rateController,
      long blockOnErrorMs,
      boolean authenticated) {
    this.exchange = exchange;
    this.connectionFactory = () -> new Connection(new Loop(
        exchangeName,
        exchange,
        rateController,
        blockOnErrorMs,
        authenticated));
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    if (alive) {
      return Completable.error(new IllegalStateException("Already connected"));
    }
    synchronized (PollingStreamingExchangeWrapper.this) {
      if (alive) {
        return Completable.error(new IllegalStateException("Already connected"));
      }
      this.alive = true;
      this.connection = connectionFactory.get();
      return connection.connect();
    }
  }

  @Override
  public boolean isAlive() {
    return alive;
  }

  @Override
  public Completable disconnect() {
    if (!alive) {
      return Completable.error(new IllegalStateException("Not connected"));
    }
    synchronized (PollingStreamingExchangeWrapper.this) {
      if (!alive) {
        return Completable.error(new IllegalStateException("Not connected"));
      }
      this.alive = false;
      Connection connection = this.connection;
      this.connection = null;
      return connection.disconnect();
    }
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    if (!alive) {
      throw new NotConnectedException();
    }
    return connection;
  }

  @Override
  public StreamingAccountService getStreamingAccountService() {
    if (!alive) {
      throw new NotConnectedException();
    }
    return connection;
  }

  @Override
  public StreamingTradeService getStreamingTradeService() {
    if (!alive) {
      throw new NotConnectedException();
    }
    return connection;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    if (exchange instanceof StreamingExchange) {
      ((StreamingExchange) exchange).useCompressedMessages(compressedMessages);
    }
  }

  @Override
  public ExchangeSpecification getExchangeSpecification() {
    return exchange.getExchangeSpecification();
  }

  @Override
  public ExchangeMetaData getExchangeMetaData() {
    return exchange.getExchangeMetaData();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {
    return exchange.getExchangeSymbols();
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return exchange.getNonceFactory();
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    return exchange.getDefaultExchangeSpecification();
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    exchange.applySpecification(exchangeSpecification);
  }

  @Override
  public MarketDataService getMarketDataService() {
    return exchange.getMarketDataService();
  }

  @Override
  public TradeService getTradeService() {
    return exchange.getTradeService();
  }

  @Override
  public AccountService getAccountService() {
    return exchange.getAccountService();
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    exchange.remoteInit();
  }

  /**
   * Everything that's scoped to a single connection.
   */
  private static class Connection implements StreamingMarketDataService,
      StreamingTradeService, StreamingAccountService {

    private final RxServiceWrapper<Loop> loop;

    private final ConcurrentMap<Subscription, AtomicInteger> refCounts = Maps.newConcurrentMap();

    private final ConcurrentMap<CurrencyPair, SharedSubject<Ticker>> tickerSubjects = Maps.newConcurrentMap();
    private final ConcurrentMap<CurrencyPair, SharedSubject<OrderBook>> orderBookSubjects = Maps.newConcurrentMap();
    private final ConcurrentMap<CurrencyPair, SharedSubject<Trade>> tradeSubjects = Maps.newConcurrentMap();
    private final ConcurrentMap<CurrencyPair, SharedSubject<UserTrade>> userTradeSubjects = Maps.newConcurrentMap();
    private final ConcurrentMap<Currency, SharedSubject<Timestamped<Balance>>> balanceSubjects = Maps
        .newConcurrentMap();
    private final ConcurrentMap<CurrencyPair, SharedSubject<Timestamped<Order>>> orderSubjects = Maps
        .newConcurrentMap();

    Connection(Loop loop) {
      this.loop = new RxServiceWrapper<>(loop);
      this.loop.getDelegate().getTickers().subscribe(this::emitTicker);
      this.loop.getDelegate().getBalances().subscribe(this::emitBalance);
      this.loop.getDelegate().getOrderBooks().subscribe(this::emitOrderBook);
      this.loop.getDelegate().getOrders().subscribe(this::emitOrder);
      this.loop.getDelegate().getTrades().subscribe(this::emitTrade);
      this.loop.getDelegate().getUserTrades().subscribe(this::emitUserTrade);
    }

    Completable connect() {
      return loop.start();
    }

    Completable disconnect() {
      return loop.stop();
    }

    void setListener(LoopListener loopListener) {
      loop.getDelegate().setLifecycleListener(loopListener);
    }

    @Override
    public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
      SharedSubject<Ticker> subject = tickerSubjects.computeIfAbsent(currencyPair, pair -> newBehaviourSubject());
      Subscription subscription = new Subscription(currencyPair, MarketDataType.TICKER);
      subscribe(subscription);
      return subject.observe().doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitTicker(TickerEvent event) {
      SharedSubject<Ticker> subject = tickerSubjects.get(event.getCurrencyPair());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(event.getTicker());
        } else {
          subject.onError(event.getError());
        }
      }
    }

    @Override
    public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
      SharedSubject<OrderBook> subject = orderBookSubjects.computeIfAbsent(currencyPair, pair -> newBehaviourSubject());
      Subscription subscription = new Subscription(currencyPair, MarketDataType.ORDERBOOK);
      subscribe(subscription);
      return subject.observe().doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitOrderBook(OrderBookEvent event) {
      SharedSubject<OrderBook> subject = orderBookSubjects.get(event.getCurrencyPair());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(event.getOrderBook());
        } else {
          subject.onError(event.getError());
        }
      }
    }

    @Override
    public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
      SharedSubject<Trade> subject = tradeSubjects.computeIfAbsent(currencyPair, pair -> newPublishSubject());
      Subscription subscription = new Subscription(currencyPair, MarketDataType.TRADES);
      subscribe(subscription);
      return subject.observe().doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitTrade(TradeEvent event) {
      SharedSubject<Trade> subject = tradeSubjects.get(event.getCurrencyPair());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(event.getTrade());
        } else {
          subject.onError(event.getError());
        }
      }
    }

    @Override
    public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
      SharedSubject<UserTrade> subject = userTradeSubjects.computeIfAbsent(currencyPair, pair -> newPublishSubject());
      Subscription subscription = new Subscription(currencyPair, MarketDataType.USER_TRADE);
      subscribe(subscription);
      Set<String> sent = Sets.newConcurrentHashSet();
      return subject.observe()
          .filter(t -> sent.add(t.getId()))
          .doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitUserTrade(UserTradeEvent event) {
      SharedSubject<UserTrade> subject = userTradeSubjects.get(event.getCurrencyPair());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(event.getTrade());
        } else {
          subject.onError(event.getError());
        }
      }
    }

    @Override
    public Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
      SharedSubject<Timestamped<Balance>> subject = balanceSubjects
          .computeIfAbsent(currency, pair -> newBehaviourSubject());
      Subscription subscription = new Subscription(currency, MarketDataType.BALANCE);
      subscribe(subscription);
      AtomicReference<Instant> mostRecent = new AtomicReference<>(Instant.EPOCH);
      return subject.observe()
          .filter(b -> {
            if (mostRecent.getAndUpdate(prev -> prev.isBefore(b.getInstant()) ? b.getInstant() : prev)
                .isBefore(b.getInstant())) {
              return true;
            } else {
              log.debug("Filtered out {} (old)", b);
              return false;
            }
          })
          .map(Timestamped::getItem)
          .doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitBalance(BalanceEvent event) {
      log.debug("Got balance: {}", event);
      SharedSubject<Timestamped<Balance>> subject = balanceSubjects.get(event.getCurrency());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(new Timestamped<>(event.getBalance(), event.getInstant()));
        } else {
          subject.onError(event.getError());
        }
      }
    }

    @Override
    public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
      SharedSubject<Timestamped<Order>> subject = orderSubjects
          .computeIfAbsent(currencyPair, pair -> newPublishSubject());
      Subscription subscription = new Subscription(currencyPair, MarketDataType.ORDER);
      subscribe(subscription);
      ConcurrentMap<String, Instant> mostRecentByOrderId = Maps.newConcurrentMap();
      return subject.observe()
          .filter(
              o -> mostRecentByOrderId.merge(o.getItem().getId(), o.getInstant(), (ov, nv) -> ov.isBefore(nv) ? nv : ov)
                  .equals(o.getInstant()))
          .map(Timestamped::getItem)
          .doOnDispose(() -> unsubscribe(subscription));
    }

    private void emitOrder(OrderEvent event) {
      SharedSubject<Timestamped<Order>> subject = orderSubjects.get(event.getCurrencyPair());
      if (subject != null) {
        if (event.getError() == null) {
          subject.onNext(new Timestamped<>(event.getOrder(), event.getInstant()));
        } else {
          subject.onError(event.getError());
        }
      }
    }

    private <T> SharedSubject<T> newBehaviourSubject() {
      return new SharedSubject<>(BehaviorSubject.create());
    }

    private <T> SharedSubject<T> newPublishSubject() {
      return new SharedSubject<>(PublishSubject.create());
    }

    private <K, T> void pushIfNew(
        Map<K, Instant> times, Subject<T> subject, T item, K key, Instant thisTiming) {
      times.compute(
          key,
          (k, previousTiming) -> {
            Instant newMostRecent = previousTiming;
            if (previousTiming == null) {
              newMostRecent = thisTiming;
            } else if (thisTiming.isAfter(previousTiming)) {
              newMostRecent = thisTiming;
              subject.onNext(item);
            }
            return newMostRecent;
          });
    }

    private boolean subscribe(Subscription subscription) {
      log.debug("... subscribing {}", subscription);
      boolean changeSubscriptions =
          refCounts
              .computeIfAbsent(subscription, s -> new AtomicInteger(0))
              .incrementAndGet()
              == 1;
      if (changeSubscriptions) {
        log.debug("   ... new subscriptions");
        loop.getDelegate().updateSubscriptions(refCounts.keySet());
      }
      return changeSubscriptions;
    }

    private boolean unsubscribe(Subscription subscription) {
      log.debug("... unsubscribing {}", subscription);
      AtomicInteger refCount = refCounts.get(subscription);
      if (refCount == null) {
        log.warn("   ... Refcount is unset for live subscription: {}", subscription);
        return true;
      }
      int newRefCount = refCount.decrementAndGet();
      log.debug("   ... refcount set to {}", newRefCount);
      if (newRefCount == 0) {
        log.debug("   ... removing subscription");
        refCounts.remove(subscription);
        loop.getDelegate().updateSubscriptions(refCounts.keySet());
        return true;
      } else {
        log.debug("   ... other subscribers still holding it open");
      }
      return false;
    }
  }

  @Value
  private static final class Timestamped<T> {

    T item;
    Instant instant;
  }

  private static final class SharedSubject<T> {

    private final Subject<T> subject;
    private final Observable<T> observable;

    SharedSubject(Subject<T> subject) {
      this.subject = subject;
      this.observable = subject
          .serialize()
          .share()
          .observeOn(Schedulers.computation());
    }

    void onNext(T t) {
      subject.onNext(t);
    }

    void onError(Throwable e) {
      subject.onError(e);
    }

    Observable<T> observe() {
      return observable;
    }
  }
}
