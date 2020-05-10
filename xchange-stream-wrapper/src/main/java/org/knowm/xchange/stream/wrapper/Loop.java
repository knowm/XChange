package org.knowm.xchange.stream.wrapper;

import static com.google.common.base.MoreObjects.firstNonNull;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.stream.Collectors.toSet;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.stream.wrapper.MarketDataType.BALANCE;
import static org.knowm.xchange.stream.wrapper.MarketDataType.PollBehaviour.POLL_ALWAYS;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.ProductSubscription.ProductSubscriptionBuilder;
import info.bitrich.xchangestream.core.StreamingExchange;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.dto.BitfinexExceptionV1;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.ExchangeUnavailableException;
import org.knowm.xchange.exceptions.FrequencyLimitExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import si.mazi.rescu.HttpStatusIOException;

/**
 * Handles the market data polling and subscription cycle for an exchange. Publishes raw events as they occur with no
 * attempt at deduplication or cleanup.
 *
 * @author Graham Crockford
 */
@Slf4j
final class Loop extends AbstractExecutionThreadService {

  private static final int MAX_TRADES = 20;
  private static final int ORDERBOOK_DEPTH = 20;
  private static final int MINUTES_BETWEEN_EXCEPTION_NOTIFICATIONS = 15;
  private static final int DEFAULT_BLOCK_ON_ERROR_MS = 10000;

  @Getter
  private final PublishSubject<TickerEvent> tickers = PublishSubject.create();
  @Getter
  private final PublishSubject<OrderBookEvent> orderBooks = PublishSubject.create();
  @Getter
  private final PublishSubject<TradeEvent> trades = PublishSubject.create();
  @Getter
  private final PublishSubject<UserTradeEvent> userTrades = PublishSubject.create();
  @Getter
  private final PublishSubject<OrderEvent> orders = PublishSubject.create();
  @Getter
  private final PublishSubject<BalanceEvent> balances = PublishSubject.create();

  private final String exchangeName;
  private final Exchange exchange;
  private final StreamingExchange streamingExchange;
  private final RateController rateController;
  private final long blockOnErrorMs;
  private final boolean authenticated;
  private final Phaser phaser = new Phaser(1);
  private LoopListener lifecycleListener = new LoopListener() {
  };
  private AccountService accountService;
  private MarketDataService marketDataService;
  private TradeService tradeService;
  private int phase;
  private boolean subscriptionsFailed;
  private Exception lastPollException;
  private LocalDateTime lastPollErrorNotificationTime;

  private AtomicReference<Set<Subscription>> nextSubscriptions = new AtomicReference<>();
  private Set<Subscription> subscriptions = ImmutableSet.of();
  private Set<Subscription> polls = ImmutableSet.of();
  private Collection<Disposable> disposables = ImmutableList.of();
  private Set<Subscription> unavailableSubscriptions = new HashSet<>();

  Loop(String exchangeName, Exchange exchange, RateController rateController,
      long blockOnErrorMs, boolean authenticated) {
    this.exchangeName = exchangeName;
    this.exchange = exchange;
    this.streamingExchange =
        exchange instanceof StreamingExchange
            ? (StreamingExchange) exchange
            : null;
    this.rateController = rateController;
    this.blockOnErrorMs = blockOnErrorMs;
    this.authenticated = authenticated;
  }

  void setLifecycleListener(LoopListener lifecycleListener) {
    this.lifecycleListener = lifecycleListener;
  }

  String getExchangeName() {
    return exchangeName;
  }

  void updateSubscriptions(Iterable<Subscription> subscriptions) {
    log.debug("Requesting update of subscriptions for {} to {}", exchangeName, subscriptions);
    nextSubscriptions.set(ImmutableSet.copyOf(subscriptions));
    wake();
  }

  @Override
  protected void run() {
    Thread.currentThread().setName("poll-loop[" + exchangeName + "]");
    log.info("{} starting", exchangeName);
    try {
      initialise();
      while (!phaser.isTerminated()) {

        // Before we check for the presence of polls, determine which phase
        // we are going to wait for if there's no work to do - i.e. the
        // next wakeup.
        phase = phaser.getPhase();
        if (phase == -1) {
          break;
        }

        loop();
      }
      log.info("{} shutting down due to termination", exchangeName);
    } catch (InterruptedException e) {
      log.info("{} shutting down due to interrupt", exchangeName);
      Thread.currentThread().interrupt();
    } catch (Exception e) {
      log.error("{} shutting down due to uncaught exception", exchangeName, e);
    } finally {
      log.debug("{} sending shutdown event", exchangeName);
      lifecycleListener.onStop();
    }
  }

  @Override
  protected void triggerShutdown() {
    log.debug("Triggering shut down of {} poll loop", exchangeName);
    phaser.arriveAndDeregister();
    phaser.forceTermination();
  }

  /**
   * This may fail when the exchange is not available, so keep trying.
   *
   * @throws InterruptedException If interrupted while sleeping.
   */
  private void initialise() throws InterruptedException {
    while (!phaser.isTerminated()) {
      try {
        if (Thread.interrupted()) {
          throw new InterruptedException();
        }
        this.accountService = exchange.getAccountService();
        this.marketDataService = exchange.getMarketDataService();
        this.tradeService = exchange.getTradeService();
        break;
      } catch (InterruptedException e) {
        throw e;
      } catch (Exception e) {
        log.error(exchangeName + " - failing initialising. Will retry in one minute.", e);
        Thread.sleep(60000);
      }
    }
  }

  private void loop() throws InterruptedException {

    // Check if there is a queued subscription change.  If so, apply it
    doSubscriptionChanges();

    // Check if we have any polling to do. If not, go to sleep until awoken
    // by a subscription change, unless we failed to process subscriptions,
    // in which case wake ourselves up in a few seconds to try again
    Set<Subscription> polls = activePolls();
    if (polls.isEmpty()) {
      suspend(phase, subscriptionsFailed);
      return;
    }

    log.debug("{} - start poll", exchangeName);
    Set<Currency> balanceCurrencies = new HashSet<>();
    for (Subscription subscription : polls) {
      if (phaser.isTerminated()) {
        break;
      }
      if (subscription.type().equals(BALANCE)) {
        // Rather than fetching balances currency-by-currency, we can fetch them all at once
        // by requesting the wallet below.
        balanceCurrencies.add(subscription.currency());
      } else {
        fetchAndBroadcast(subscription);
      }
    }

    if (phaser.isTerminated()) {
      return;
    }

    if (!balanceCurrencies.isEmpty()) {
      manageExchangeExceptions(
          "Balances",
          () -> {
            Instant requestInstant = Instant.now();
            fetchBalances(balanceCurrencies)
                .forEach(balance -> balances.onNext(BalanceEvent.of(balance, requestInstant)));
          },
          () -> FluentIterable.from(polls).filter(s -> s.type().equals(BALANCE)),
          e -> balanceCurrencies.forEach(currency -> balances.onNext(BalanceEvent.of(currency, e))));
    }
  }

  private void suspend(int phase, boolean failed) throws InterruptedException {
    log.debug("{} - poll going to sleep", exchangeName);
    try {
      if (failed) {
        phaser.awaitAdvanceInterruptibly(phase, blockOnErrorMs, TimeUnit.MILLISECONDS);
      } else {
        log.debug("{} - sleeping until phase {}", exchangeName, phase);
        lifecycleListener.onBlocked();
        phaser.awaitAdvanceInterruptibly(phase);
        log.debug("{} - poll woken up on request", exchangeName);
      }
    } catch (TimeoutException e) {
      // fine
    } catch (InterruptedException e) {
      throw e;
    } catch (Exception e) {
      log.error("Failure in phaser wait for {}", exchangeName, e);
    }
  }

  private void wake() {
    int phase = phaser.arrive();
    log.debug("Progressing to phase {}", phase);
  }

  private void manageExchangeExceptions(
      String dataDescription,
      ThrowingRunnable runnable,
      Supplier<Iterable<Subscription>> toUnsubscribe,
      Consumer<Throwable> onError)
      throws InterruptedException {
    try {
      runnable.run();

    } catch (InterruptedException e) {
      throw e;

    } catch (UnsupportedOperationException e) {

      // Disable the feature since the exchange doesn't have support for it.
      String message = String.format("%s not available: %s (%s)", dataDescription,
          e.getClass().getSimpleName(),
          exceptionMessage(e));
      log.error(message);
      onError.accept(new UnsupportedOperationException(message, e));
      Iterables.addAll(unavailableSubscriptions, toUnsubscribe.get());

    } catch (SocketTimeoutException
        | SocketException
        | ExchangeUnavailableException
        | NonceException e) {

      // Managed connectivity issues.
      log.warn(
          "Throttling {} - {} ({}) when fetching {}",
          exchangeName,
          e.getClass().getSimpleName(),
          exceptionMessage(e),
          dataDescription);
      rateController.throttle();

    } catch (HttpStatusIOException e) {

      handleHttpStatusException(dataDescription, e);

    } catch (RateLimitExceededException | FrequencyLimitExceededException e) {

      log.warn(
          "Getting rate limiting errors on {} when fetching {}. Pausing access and will resume at a lower rate.",
          exchangeName, dataDescription);
      rateController.backoff();
      rateController.pause();

    } catch (ExchangeException e) {
      if (e.getCause() instanceof HttpStatusIOException) {
        // TODO Bitmex is inappropriately wrapping these and should be fixed
        // for consistency. In the meantime...
        handleHttpStatusException(dataDescription, (HttpStatusIOException) e.getCause());
      } else {
        handleUnknownPollException(e);
      }
    } catch (BitfinexExceptionV1 e) {
      handleUnknownPollException(
          new ExchangeException(
              "Bitfinex exception: " + exceptionMessage(e) + " (error code=" + e.getError() + ")",
              e));
    } catch (Exception e) {
      handleUnknownPollException(e);
    }
  }

  private void handleHttpStatusException(String dataDescription, HttpStatusIOException e) {
    if (e.getHttpStatusCode() == 408
        || e.getHttpStatusCode() == 502
        || e.getHttpStatusCode() == 504
        || e.getHttpStatusCode() == 521) {
      // Usually these are rejections at CloudFlare (Coinbase Pro & Kraken being common cases) or
      // connection timeouts.
      if (log.isWarnEnabled()) {
        log.warn(
            "Throttling {} - failed at gateway ({} - {}) when fetching {}",
            exchangeName,
            e.getHttpStatusCode(),
            exceptionMessage(e),
            dataDescription);
      }
      rateController.throttle();
    } else {
      handleUnknownPollException(e);
    }
  }

  private String exceptionMessage(Throwable e) {
    if (e.getMessage() == null) {
      if (e.getCause() == null) {
        return "No description";
      } else {
        return exceptionMessage(e.getCause());
      }
    } else {
      return e.getMessage();
    }
  }

  private void handleUnknownPollException(Exception e) {
    LocalDateTime now = now();
    String exceptionMessage = exceptionMessage(e);
    if (lastPollException == null
        || !lastPollException.getClass().equals(e.getClass())
        || !firstNonNull(exceptionMessage(lastPollException), "").equals(exceptionMessage)
        || lastPollErrorNotificationTime.until(now, MINUTES)
        > MINUTES_BETWEEN_EXCEPTION_NOTIFICATIONS) {
      lastPollErrorNotificationTime = now;
      log.error("Throttling access to {} due to server error ({} - {})", exchangeName, e.getClass().getSimpleName(),
          exceptionMessage);
    } else {
      log.error("Repeated error fetching data for {} ({})", exchangeName, exceptionMessage);
    }
    lastPollException = e;
    rateController.throttle();
  }

  /**
   * Actually performs the subscription changes. Occurs synchronously in the poll loop.
   */
  private void doSubscriptionChanges() {
    log.debug("{} - start subscription check", exchangeName);
    subscriptionsFailed = false;

    // Pull the subscription change off the queue. If there isn't one,
    // we're done
    Set<Subscription> newSubscriptions = nextSubscriptions.getAndSet(null);
    if (newSubscriptions == null) {
      return;
    }

    try {

      // Get the current subscriptions
      Set<Subscription> oldSubscriptions =
          Streams.concat(subscriptions.stream(), polls.stream())
              .collect(toSet());

      // If there's no difference, we're good, done
      if (newSubscriptions.equals(oldSubscriptions)) {
        return;
      }

      // Otherwise, let's crack on
      log.info(
          "{} - updating subscriptions to: {} from {}",
          exchangeName,
          newSubscriptions,
          oldSubscriptions);

      // Disconnect any streaming exchanges where the tickers currently
      // subscribed mismatch the ones we want.
      if (!oldSubscriptions.isEmpty()) {
        disconnect();
      }

      // Add new subscriptions if we have any
      if (newSubscriptions.isEmpty()) {
        polls = ImmutableSet.of();
        log.debug("{} - polls cleared", exchangeName);
      } else {
        subscribe(newSubscriptions);
      }
    } catch (Exception e) {
      subscriptionsFailed = true;
      log.error("Error updating subscriptions", e);
      if (nextSubscriptions.compareAndSet(null, newSubscriptions)) {
        wake();
      }
      throw e;
    }
  }

  private Set<Subscription> activePolls() {
    return polls.stream()
        .filter(s -> !unavailableSubscriptions.contains(s))
        .collect(toSet());
  }

  private void disconnect() {
    if (streamingExchange != null) {
      SafelyDispose.of(disposables);
      disposables = ImmutableSet.of();
      try {
        streamingExchange.disconnect().blockingAwait();
      } catch (Exception e) {
        log.error("Error disconnecting from " + exchangeName, e);
      }
    }
  }

  private void subscribe(Set<Subscription> newSubscriptions) {
    Builder<Subscription> pollingBuilder = ImmutableSet.builder();
    if (streamingExchange != null) {
      Set<Subscription> remainingSubscriptions =
          openSubscriptionsWherePossible(newSubscriptions);
      pollingBuilder.addAll(remainingSubscriptions);
    } else {
      pollingBuilder.addAll(newSubscriptions);
    }
    polls = pollingBuilder.build();
    log.debug("{} - polls now set to: {}", exchangeName, polls);
  }

  private Set<Subscription> openSubscriptionsWherePossible(Set<Subscription> newSubscriptions) {

    connectExchange(newSubscriptions);

    HashSet<Subscription> connected = new HashSet<>(newSubscriptions);
    Builder<Subscription> remainder = ImmutableSet.builder();
    List<Disposable> disposables = new ArrayList<>();

    for (Subscription s : newSubscriptions) {
      if (s.type().getPollBehaviour() == POLL_ALWAYS) {
        remainder.add(s);
      }
      try {
        disposables.add(connectSubscription(s));
      } catch (UnsupportedOperationException | ExchangeSecurityException e) {
        log.debug(
            "Not subscribing to {} on socket due to {}: {}. Falling back to polling.",
            s.key(),
            e.getClass().getSimpleName(),
            e.getMessage());
        remainder.add(s);
        connected.remove(s);
      }
    }

    this.subscriptions = Collections.unmodifiableSet(connected);
    this.disposables = disposables;
    return remainder.build();
  }

  private Disposable connectSubscription(Subscription sub) {
    switch (sub.type()) {
      case ORDERBOOK:
        return streamingExchange
            .getStreamingMarketDataService()
            .getOrderBook(sub.currencyPair())
            .map(ob -> OrderBookEvent.of(sub.currencyPair(), ob))
            .subscribe(orderBooks::onNext, e -> orderBooks.onNext(OrderBookEvent.of(sub.currencyPair(), e)));
      case TICKER:
        return streamingExchange
            .getStreamingMarketDataService()
            .getTicker(sub.currencyPair())
            .map(TickerEvent::of)
            .subscribe(tickers::onNext, e -> tickers.onNext(TickerEvent.of(sub.currencyPair(), e)));
      case TRADES:
        return streamingExchange
            .getStreamingMarketDataService()
            .getTrades(sub.currencyPair())
            .map(this::convertBinanceOrderType)
            .map(TradeEvent::of)
            .subscribe(trades::onNext, e -> trades.onNext(TradeEvent.of(sub.currencyPair(), e)));
      case USER_TRADE:
        return streamingExchange
            .getStreamingTradeService()
            .getUserTrades(sub.currencyPair())
            .map(UserTradeEvent::of)
            .subscribe(userTrades::onNext, e -> userTrades.onNext(UserTradeEvent.of(sub.currencyPair(), e)));
      case ORDER:
        return streamingExchange
            .getStreamingTradeService()
            .getOrderChanges(sub.currencyPair())
            .map(o -> OrderEvent.of(o, Instant.now()))
            .subscribe(orders::onNext, e -> orders.onNext(OrderEvent.of(sub.currencyPair(), e)));
      case BALANCE:
        return streamingExchange
            .getStreamingAccountService()
            .getBalanceChanges(sub.currency(), "exchange") // TODO bitfinex walletId.
            .map(b -> BalanceEvent.of(b, Instant.now()))
            .subscribe(balances::onNext, e -> balances.onNext(BalanceEvent.of(sub.currency(), e)));
      default:
        throw new NotAvailableFromExchangeException();
    }
  }

  /**
   * TODO Temporary fix for https://github.com/knowm/XChange/issues/2468#issuecomment-441440035
   */
  private Trade convertBinanceOrderType(Trade t) {
    if (exchangeName.equalsIgnoreCase("binance")) {
      return Trade.Builder.from(t).type(t.getType() == BID ? ASK : BID).build();
    } else {
      return t;
    }
  }

  private void connectExchange(Collection<Subscription> subscriptionsForExchange) {
    if (subscriptionsForExchange.isEmpty()) {
      return;
    }
    log.info("Connecting to exchange: {}", exchangeName);
    ProductSubscriptionBuilder builder = ProductSubscription.create();
    subscriptionsForExchange.forEach(
        s -> {
          switch (s.type()) {
            case TICKER:
              builder.addTicker(s.currencyPair());
              break;
            case ORDERBOOK:
              builder.addOrderbook(s.currencyPair());
              break;
            case TRADES:
              builder.addTrades(s.currencyPair());
              break;
            case ORDER:
              if (authenticated) {
                builder.addOrders(s.currencyPair());
              }
              break;
            case USER_TRADE:
              if (authenticated) {
                builder.addUserTrades(s.currencyPair());
              }
              break;
            case BALANCE:
              if (authenticated) {
                builder.addBalances(s.currency());
              }
              break;
            default:
              // Not available from socket
          }
        });
    rateController.acquire();
    streamingExchange.connect(builder.build()).blockingAwait();
    log.info("Connected to exchange: {}", exchangeName);
  }

  private Iterable<Balance> fetchBalances(Collection<Currency> currencies) throws IOException {
    log.debug("Fetching balances for {}", currencies);
    Map<Currency, Balance> result = new HashMap<>();
    currencies.forEach(currency -> result.put(currency, Balance.zero(currency)));
    wallet().getBalances().values().stream()
        .filter(balance -> currencies.contains(balance.getCurrency()))
        .forEach(balance -> result.put(balance.getCurrency(), balance));
    return result.values();
  }

  private Wallet wallet() throws IOException {
    rateController.acquire();
    Wallet wallet;
    // TODO make these exchanges consistently return the main trading wallet if you just ask for .getWallet().
    if (exchangeName.equalsIgnoreCase("bitfinex")) {
      wallet = accountService.getAccountInfo().getWallet("exchange");
    } else if (exchangeName.equals("kucoin")) {
      wallet = accountService.getAccountInfo().getWallet("trade");
      if (wallet == null) {
        wallet = accountService.getAccountInfo().getWallet();
      }
    } else {
      wallet = accountService.getAccountInfo().getWallet();
    }
    if (wallet == null) {
      throw new IllegalStateException("No wallet returned");
    }
    return wallet;
  }

  private void fetchAndBroadcast(Subscription subscription)
      throws InterruptedException {
    rateController.acquire();
    manageExchangeExceptions(
        subscription.key(),
        () -> {
          switch (subscription.type()) {
            case TICKER:
              pollAndEmitTicker(subscription.currencyPair());
              break;
            case ORDERBOOK:
              pollAndEmitOrderbook(subscription.currencyPair());
              break;
            case TRADES:
              pollAndEmitTrades(subscription.currencyPair());
              break;
            case USER_TRADE:
              pollAndEmitUserTradeHistory(subscription.currencyPair());
              break;
            case ORDER:
              pollAndEmitOpenOrders(subscription.currencyPair());
              break;
            default:
              throw new IllegalStateException(
                  "Market data type " + subscription.type() + " not supported in this way");
          }
        },
        () -> ImmutableList.of(subscription),
        e -> {
          switch (subscription.type()) {
            case TICKER:
              tickers.onNext(TickerEvent.of(subscription.currencyPair(), e));
              break;
            case ORDERBOOK:
              orderBooks.onNext(OrderBookEvent.of(subscription.currencyPair(), e));
              break;
            case TRADES:
              trades.onNext(TradeEvent.of(subscription.currencyPair(), e));
              break;
            case USER_TRADE:
              userTrades.onNext(UserTradeEvent.of(subscription.currencyPair(), e));
              break;
            case ORDER:
              orders.onNext(OrderEvent.of(subscription.currencyPair(), e));
              break;
            case BALANCE:
              balances.onNext(BalanceEvent.of(subscription.currency(), e));
              break;
            default:
              throw new IllegalStateException(
                  "Market data type " + subscription.type() + " not supported in this way");
          }
        });
  }

  private void pollAndEmitUserTradeHistory(CurrencyPair currencyPair) throws IOException {
    TradeHistoryParams tradeHistoryParams = tradeHistoryParams(currencyPair);
    tradeService
        .getTradeHistory(tradeHistoryParams)
        .getUserTrades()
        .forEach(t -> userTrades.onNext(UserTradeEvent.of(t)));
  }

  @SuppressWarnings("unchecked")
  private void pollAndEmitOpenOrders(CurrencyPair currencyPair) throws IOException {
    OpenOrdersParams openOrdersParams = openOrdersParams(currencyPair);

    Date originatingTimestamp = new Date();
    OpenOrders fetched = tradeService.getOpenOrders(openOrdersParams);

    // TODO GDAX PR required
    if (exchangeName.equalsIgnoreCase("coinbasepro") || exchangeName.equalsIgnoreCase("gdax")) {
      List<LimitOrder> filteredOpen =
          fetched.getOpenOrders().stream().filter(openOrdersParams::accept)
              .collect(Collectors.toList());
      List<? extends Order> filteredHidden = new ArrayList<>(fetched.getHiddenOrders());
      fetched = new OpenOrders(filteredOpen, (List<Order>) filteredHidden);
    }

    fetched.getAllOpenOrders()
        .forEach(o -> orders.onNext(OrderEvent.of(o, originatingTimestamp.toInstant())));
  }

  private void pollAndEmitTrades(CurrencyPair currencyPair) throws IOException {
    marketDataService
        .getTrades(currencyPair)
        .getTrades()
        .forEach(t -> trades.onNext(TradeEvent.of(t)));
  }

  private void pollAndEmitOrderbook(CurrencyPair currencyPair) throws IOException {
    OrderBook orderBook =
        marketDataService.getOrderBook(currencyPair, exchangeOrderbookArgs());
    orderBooks.onNext(OrderBookEvent.of(currencyPair, orderBook));
  }

  private Object[] exchangeOrderbookArgs() {
    // TODO
    if (exchangeName.equals("bitmex")) {
      return new Object[]{};
    } else {
      return new Object[]{ORDERBOOK_DEPTH, ORDERBOOK_DEPTH};
    }
  }

  private void pollAndEmitTicker(CurrencyPair currencyPair) throws IOException {
    tickers.onNext(TickerEvent.of(marketDataService.getTicker(currencyPair)));
  }

  private TradeHistoryParams tradeHistoryParams(CurrencyPair currencyPair) {
    TradeHistoryParams params;

    // TODO fix with pull requests
    if (exchangeName.equals("bitmex") || exchangeName.equals("coinbasepro") || exchangeName
        .equals("gdax")) {
      params =
          new TradeHistoryParamCurrencyPair() {

            private CurrencyPair pair;

            @Override
            public CurrencyPair getCurrencyPair() {
              return pair;
            }

            @Override
            public void setCurrencyPair(CurrencyPair pair) {
              this.pair = pair;
            }
          };
    } else {
      params = tradeService.createTradeHistoryParams();
    }

    if (params instanceof TradeHistoryParamCurrencyPair) {
      ((TradeHistoryParamCurrencyPair) params)
          .setCurrencyPair(currencyPair);
    } else {
      throw new UnsupportedOperationException(
          "Don't know how to read user trades on this exchange: "
              + exchangeName);
    }
    if (params instanceof TradeHistoryParamLimit) {
      ((TradeHistoryParamLimit) params).setLimit(MAX_TRADES);
    }
    if (params instanceof TradeHistoryParamPaging) {
      ((TradeHistoryParamPaging) params).setPageLength(MAX_TRADES);
      ((TradeHistoryParamPaging) params).setPageNumber(0);
    }
    return params;
  }

  private OpenOrdersParams openOrdersParams(CurrencyPair currencyPair) {
    OpenOrdersParams params = null;
    try {
      params = tradeService.createOpenOrdersParams();
    } catch (NotYetImplementedForExchangeException e) {
      // Fiiiiine Bitmex
    }
    if (params == null) {
      // Bitfinex & Bitmex
      params = new DefaultOpenOrdersParamCurrencyPair(currencyPair);
    } else if (params instanceof OpenOrdersParamCurrencyPair) {
      ((OpenOrdersParamCurrencyPair) params).setCurrencyPair(currencyPair);
    } else {
      throw new UnsupportedOperationException(
          "Don't know how to read open orders on this exchange: "
              + exchangeName);
    }
    return params;
  }

  @Value
  static final class TickerEvent {

    static TickerEvent of(Ticker ticker) {
      return new TickerEvent(ticker.getCurrencyPair(), ticker, null);
    }

    static TickerEvent of(CurrencyPair currencyPair, Throwable error) {
      return new TickerEvent(currencyPair, null, error);
    }

    CurrencyPair currencyPair;
    Ticker ticker;
    Throwable error;
  }

  @Value
  static final class OrderBookEvent {

    static OrderBookEvent of(CurrencyPair currencyPair, OrderBook orderBook) {
      return new OrderBookEvent(currencyPair, orderBook, null);
    }

    static OrderBookEvent of(CurrencyPair currencyPair, Throwable error) {
      return new OrderBookEvent(currencyPair, null, error);
    }

    CurrencyPair currencyPair;
    OrderBook orderBook;
    Throwable error;
  }

  @Value
  static final class TradeEvent {

    static TradeEvent of(Trade trade) {
      return new TradeEvent(trade.getCurrencyPair(), trade, null);
    }

    static TradeEvent of(CurrencyPair currencyPair, Throwable error) {
      return new TradeEvent(currencyPair, null, error);
    }

    CurrencyPair currencyPair;
    Trade trade;
    Throwable error;
  }

  @Value
  static final class UserTradeEvent {

    static UserTradeEvent of(UserTrade trade) {
      return new UserTradeEvent(trade.getCurrencyPair(), trade, null);
    }

    static UserTradeEvent of(CurrencyPair currencyPair, Throwable error) {
      return new UserTradeEvent(currencyPair, null, error);
    }

    CurrencyPair currencyPair;
    UserTrade trade;
    Throwable error;
  }

  @Value
  static final class BalanceEvent {

    static BalanceEvent of(Balance balance, Instant instant) {
      return new BalanceEvent(balance.getCurrency(), balance, instant, null);
    }

    static BalanceEvent of(Currency currency, Throwable error) {
      return new BalanceEvent(currency, null, null, error);
    }

    Currency currency;
    Balance balance;
    Instant instant;
    Throwable error;
  }

  @Value
  static final class OrderEvent {

    static OrderEvent of(Order order, Instant instant) {
      return new OrderEvent(order.getCurrencyPair(), order, instant, null);
    }

    static OrderEvent of(CurrencyPair currencyPair, Throwable error) {
      return new OrderEvent(currencyPair, null, null, error);
    }

    CurrencyPair currencyPair;
    Order order;
    Instant instant;
    Throwable error;
  }
}
