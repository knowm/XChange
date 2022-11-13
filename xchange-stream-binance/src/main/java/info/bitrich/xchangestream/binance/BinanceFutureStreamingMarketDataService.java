package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.binance.BinanceSubscriptionType.KLINE;
import static info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper.getObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import info.bitrich.xchangestream.binance.dto.*;
import info.bitrich.xchangestream.binance.exceptions.UpFrontSubscriptionRequiredException;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.*;
import org.knowm.xchange.binance.service.BinanceFutureMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.knowm.xchange.instrument.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceFutureStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceFutureStreamingMarketDataService.class);

  private static final JavaType TICKER_TYPE = getTickerType();
  private static final JavaType BOOK_TICKER_TYPE = getBookTickerType();
  private static final JavaType TRADE_TYPE = getTradeType();
  private static final JavaType DEPTH_TYPE = getDepthType();
  private static final JavaType KLINE_TYPE = getKlineType();

  /**
   * A scheduler for initialisation of binance order book snapshots, which is delegated to a
   * dedicated thread in order to avoid blocking of the Web Socket threads.
   */
  private static final Scheduler bookSnapshotsScheduler =
      Schedulers.from(
          Executors.newSingleThreadExecutor(
              new ThreadFactoryBuilder()
                  .setDaemon(true)
                  .setNameFormat("binancefuture-book-snapshots-%d")
                  .build()));

  private final BinanceStreamingService service;
  private final String orderBookUpdateFrequencyParameter;
  private final boolean realtimeOrderBookTicker;
  private final int oderBookFetchLimitParameter;

  private final Map<CurrencyPair, Observable<BinanceTicker24h>> tickerSubscriptions;
  private final Map<CurrencyPair, Observable<BinanceBookTicker>> bookTickerSubscriptions;
  private final Map<Instrument, Observable<OrderBook>> orderbookSubscriptions;
  private final Map<Instrument, Observable<BinanceRawTrade>> tradeSubscriptions;
  private final Map<Instrument, Observable<OrderBookUpdate>> orderBookUpdatesSubscriptions;
  private final Map<Instrument, Map<KlineInterval, Observable<BinanceKline>>> klineSubscriptions;
  private final Map<Instrument, Observable<DepthBinanceFutureWebSocketTransaction>>
      orderBookRawUpdatesSubscriptions;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final BinanceFutureMarketDataService marketDataService;
  private final Runnable onApiCall;

  private final AtomicBoolean fallenBack = new AtomicBoolean();
  private final AtomicReference<Runnable> fallbackOnApiCall = new AtomicReference<>(() -> {});

  public BinanceFutureStreamingMarketDataService(
      BinanceStreamingService service,
      BinanceFutureMarketDataService marketDataService,
      Runnable onApiCall,
      final String orderBookUpdateFrequencyParameter,
      boolean realtimeOrderBookTicker,
      int oderBookFetchLimitParameter) {
    this.service = service;
    this.orderBookUpdateFrequencyParameter = orderBookUpdateFrequencyParameter;
    this.realtimeOrderBookTicker = realtimeOrderBookTicker;
    this.oderBookFetchLimitParameter = oderBookFetchLimitParameter;
    this.marketDataService = marketDataService;
    this.onApiCall = onApiCall;
    this.tickerSubscriptions = new ConcurrentHashMap<>();
    this.bookTickerSubscriptions = new ConcurrentHashMap<>();
    this.orderbookSubscriptions = new ConcurrentHashMap<>();
    this.tradeSubscriptions = new ConcurrentHashMap<>();
    this.orderBookUpdatesSubscriptions = new ConcurrentHashMap<>();
    this.orderBookRawUpdatesSubscriptions = new ConcurrentHashMap<>();
    this.klineSubscriptions = new ConcurrentHashMap<>();
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getOrderBook().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return orderbookSubscriptions.computeIfAbsent(currencyPair, this::initOrderBookIfAbsent);
  }

  private Observable<OrderBook> initOrderBookIfAbsent(Instrument currencyPair) {
    orderBookRawUpdatesSubscriptions.computeIfAbsent(
        currencyPair, s -> triggerObservableBody(rawOrderBookUpdates(currencyPair)));
    return createOrderBookObservable(currencyPair);
  }

  public Observable<BinanceTicker24h> getRawTicker(CurrencyPair currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTicker().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return tickerSubscriptions.computeIfAbsent(
        currencyPair, s -> triggerObservableBody(rawTickerStream(currencyPair)).share());
  }

  public Observable<BinanceBookTicker> getRawBookTicker(CurrencyPair currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTicker().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return bookTickerSubscriptions.computeIfAbsent(
        currencyPair, s -> triggerObservableBody(rawBookTickerStream(currencyPair)).share());
  }

  public Observable<BinanceRawTrade> getRawTrades(CurrencyPair currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTrades().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return tradeSubscriptions.computeIfAbsent(
        currencyPair, s -> triggerObservableBody(rawTradeStream(currencyPair)).share());
  }

  public Observable<BinanceKline> getKlines(CurrencyPair currencyPair, KlineInterval interval) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getKlineSubscription().contains(currencyPair, interval)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return klineSubscriptions
        .compute(
            currencyPair,
            (c, v) -> {
              Map<KlineInterval, Observable<BinanceKline>> intervalMap = createMapIfNull(v);

              intervalMap.computeIfAbsent(
                  interval,
                  i -> triggerObservableBody(klinesStream(currencyPair, interval)).share());

              return intervalMap;
            })
        .get(interval);
  }

  private static <K, V> Map<K, V> createMapIfNull(Map<K, V> map) {
    return map == null ? new ConcurrentHashMap<>() : map;
  }

  private Observable<BinanceKline> klinesStream(CurrencyPair currencyPair, KlineInterval interval) {
    return service
        .subscribeChannel(
            getChannelPrefix(currencyPair) + "@" + KLINE.getType() + "_" + interval.code())
        .map(it -> this.<KlineBinanceWebSocketTransaction>readTransaction(it, KLINE_TYPE, "kline"))
        .filter(
            transaction ->
                transaction.getData().getBinanceKline().getCurrencyPair().equals(currencyPair)
                    && transaction.getData().getBinanceKline().getInterval().equals(interval))
        .map(transaction -> transaction.getData().getBinanceKline());
  }

  /**
   * Api to get binance incremental order book updates. As binance websocket provides only api to
   * get incremental updates {@link #getOrderBook(CurrencyPair, Object...)} have to build book from
   * rest and websocket which leads to delay before the order book will be received by subscriber.
   * This api provides the ability to start receiving updates immediately. It is allowed to
   * subscribe to this api and {@link #getOrderBook(CurrencyPair, Object...)} at the same time.
   */
  public Observable<OrderBookUpdate> getOrderBookUpdates(
      CurrencyPair currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getOrderBook().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return orderBookUpdatesSubscriptions.computeIfAbsent(
        currencyPair, this::initOrderBookUpdateIfAbsent);
  }

  private Observable<OrderBookUpdate> initOrderBookUpdateIfAbsent(Instrument currencyPair) {
    orderBookRawUpdatesSubscriptions.computeIfAbsent(
        currencyPair, s -> triggerObservableBody(rawOrderBookUpdates(currencyPair)));
    return createOrderBookUpdatesObservable(currencyPair);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    if (realtimeOrderBookTicker) {
      return getRawBookTicker(currencyPair).map(BinanceBookTicker::toTicker);
    }
    return getRawTicker(currencyPair).map(BinanceTicker24h::toTicker);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return getRawTrades(currencyPair, args)
        .map(
            rawTrade ->
                new Trade.Builder()
                    .type(BinanceAdapters.convertType(rawTrade.isBuyerMarketMaker()))
                    .originalAmount(rawTrade.getQuantity())
                    .instrument(currencyPair)
                    .price(rawTrade.getPrice())
                    .makerOrderId(getMakerOrderId(rawTrade))
                    .takerOrderId(getTakerOrderId(rawTrade))
                    .timestamp(new Date(rawTrade.getTimestamp()))
                    .id(String.valueOf(rawTrade.getTradeId()))
                    .build());
  }

  private String getMakerOrderId(BinanceRawTrade trade) {
    return String.valueOf(
        trade.isBuyerMarketMaker() ? trade.getBuyerOrderId() : trade.getSellerOrderId());
  }

  private String getTakerOrderId(BinanceRawTrade trade) {
    return String.valueOf(
        trade.isBuyerMarketMaker() ? trade.getSellerOrderId() : trade.getBuyerOrderId());
  }

  private Observable<OrderBookUpdate> createOrderBookUpdatesObservable(Instrument currencyPair) {
    return orderBookRawUpdatesSubscriptions
        .get(currencyPair)
        .flatMap(
            depthTransaction ->
                observableFromStream(extractOrderBookUpdates(currencyPair, depthTransaction)))
        .share();
  }

  private String channelFromCurrency(Instrument currencyPair, String subscriptionType) {
    String currency = getChannelPrefix(currencyPair);
    String currencyChannel = currency + "@" + subscriptionType;

    if (BinanceSubscriptionType.DEPTH.getType().equals(subscriptionType)) {
      return currencyChannel + orderBookUpdateFrequencyParameter;
    } else {
      return currencyChannel;
    }
  }

  private String getChannelPrefix(Instrument currencyPair) {
    return String.join("", currencyPair.toString().split("/")).toLowerCase();
  }

  /**
   * Registers subscriptions with the streaming service for the given products.
   *
   * <p>As we receive messages as soon as the connection is open, we need to register subscribers to
   * handle these before the first messages arrive.
   */
  public void openSubscriptions(
      ProductSubscription productSubscription, KlineSubscription klineSubscription) {
    klineSubscription.getKlines().forEach((this::initKlineSubscription));
    productSubscription.getTicker().forEach(this::initTickerSubscription);
    productSubscription.getOrderBook().forEach(this::initRawOrderBookUpdatesSubscription);
    productSubscription.getTrades().forEach(this::initTradeSubscription);
  }

  private void initKlineSubscription(CurrencyPair currencyPair, Set<KlineInterval> klineIntervals) {
    klineSubscriptions.compute(
        currencyPair,
        (c, v) -> {
          Map<KlineInterval, Observable<BinanceKline>> intervalMap = createMapIfNull(v);
          klineIntervals.forEach(
              interval ->
                  intervalMap.put(
                      interval, triggerObservableBody(klinesStream(currencyPair, interval))));
          return intervalMap;
        });
  }

  /**
   * Live Unsubscription that should be called in the Observable.doOnDispose(). This is required to
   * stop receiving data from the stream. This method also clear the subscription from the
   * appropriate map.
   */
  public void unsubscribe(Instrument currencyPair, BinanceSubscriptionType subscriptionType) {
    if (subscriptionType == KLINE) {
      klineSubscriptions.computeIfPresent(
          currencyPair,
          (k, intervalMap) -> {
            intervalMap
                .keySet()
                .forEach(klineInterval -> unsubscribeKline(currencyPair, klineInterval));
            return null;
          });
    } else {
      unsubscribe(currencyPair, subscriptionType, null);
    }
  }

  public void unsubscribeKline(Instrument currencyPair, KlineInterval klineInterval) {
    unsubscribe(currencyPair, KLINE, klineInterval);
  }

  private void unsubscribe(
      Instrument currencyPair,
      BinanceSubscriptionType subscriptionType,
      KlineInterval klineInterval) {

    if (!service.isLiveSubscriptionEnabled()) {
      throw new UnsupportedOperationException(
          "Unsubscribe not supported for Binance when live Subscription/Unsubscription is disabled. "
              + "Call BinanceStreamingExchange.enableLiveSubscription() to active it");
    }
    String channelId = getChannelId(currencyPair, subscriptionType, klineInterval);
    this.service.unsubscribeChannel(channelId);

    switch (subscriptionType) {
      case DEPTH:
        orderbookSubscriptions.remove(currencyPair);
        orderBookUpdatesSubscriptions.remove(currencyPair);
        orderBookRawUpdatesSubscriptions.remove(currencyPair);
        break;
      case TRADE:
        tradeSubscriptions.remove(currencyPair);
        break;
      case TICKER:
        tickerSubscriptions.remove(currencyPair);
        break;
      case BOOK_TICKER:
        bookTickerSubscriptions.remove(currencyPair);
        break;
      case KLINE:
        klineSubscriptions.computeIfPresent(
            currencyPair,
            (k, intervalMap) -> {
              intervalMap.remove(klineInterval);
              return intervalMap;
            });
      default:
        throw new IllegalArgumentException(
            "Subscription type not supported to unsubscribe from stream");
    }
  }

  private String getChannelId(
      Instrument currencyPair,
      BinanceSubscriptionType subscriptionType,
      KlineInterval klineInterval) {
    return getChannelPrefix(currencyPair)
        + "@"
        + subscriptionType.getType()
        + (klineInterval != null ? "_" + klineInterval.code() : "");
  }

  private void initTradeSubscription(CurrencyPair currencyPair) {
    tradeSubscriptions.put(
        currencyPair, triggerObservableBody(rawTradeStream(currencyPair)).share());
  }

  private void initTickerSubscription(CurrencyPair currencyPair) {
    if (realtimeOrderBookTicker) {
      bookTickerSubscriptions.put(
          currencyPair, triggerObservableBody(rawBookTickerStream(currencyPair)).share());
    } else {
      tickerSubscriptions.put(
          currencyPair, triggerObservableBody(rawTickerStream(currencyPair)).share());
    }
  }

  private void initRawOrderBookUpdatesSubscription(CurrencyPair currencyPair) {
    orderBookRawUpdatesSubscriptions.put(
        currencyPair, triggerObservableBody(rawOrderBookUpdates(currencyPair)));
  }

  private Observable<BinanceTicker24h> rawTickerStream(CurrencyPair currencyPair) {
    return service
        .subscribeChannel(
            channelFromCurrency(currencyPair, BinanceSubscriptionType.TICKER.getType()))
        .map(
            it ->
                this.<TickerBinanceWebsocketTransaction>readTransaction(it, TICKER_TYPE, "ticker"))
        .filter(transaction -> transaction.getData().getCurrencyPair().equals(currencyPair))
        .map(transaction -> transaction.getData().getTicker());
  }

  private Observable<BinanceBookTicker> rawBookTickerStream(CurrencyPair currencyPair) {
    return service
        .subscribeChannel(
            channelFromCurrency(currencyPair, BinanceSubscriptionType.BOOK_TICKER.getType()))
        .map(
            it ->
                this.<BookTickerBinanceWebSocketTransaction>readTransaction(
                    it, BOOK_TICKER_TYPE, "book ticker"))
        .filter(transaction -> transaction.getData().getCurrencyPair().equals(currencyPair))
        .map(transaction -> transaction.getData().getTicker());
  }

  /**
   * Encapsulates a state of the order book subscription, including the order book initial snapshot
   * and further updates with received deltas.
   *
   * <p>Related doc: https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream
   */
  @SuppressWarnings("Convert2MethodRef")
  private final class OrderBookSubscription implements Disposable {
    private final Instrument currencyPair;
    private final Observable<DepthBinanceFutureWebSocketTransaction> deltasObservable;

    private final Queue<DepthBinanceFutureWebSocketTransaction> deltasBuffer = new LinkedList<>();
    private final BehaviorSubject<OrderBook> booksSubject = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    /**
     * Helps to keep integrity of book snapshot which is initialized and patched on different
     * threads.
     */
    private final Object bookIntegrityMonitor = new Object();

    private OrderBook book;
    private long finalUpdateidPrev = 0L;

    private OrderBookSubscription(
        Observable<DepthBinanceFutureWebSocketTransaction> deltasObservable,
        Instrument currencyPair) {
      this.deltasObservable = deltasObservable;
      this.currencyPair = currencyPair;
    }

    public Observable<OrderBook> connect() {
      if (isDisposed())
        throw new IllegalStateException(
            "Disposed before, use a new instance to connect next time.");

      disposables.add(asyncInitializeOrderBookSnapshot());

      deltasObservable
          .doOnNext(
              delta -> {
                synchronized (bookIntegrityMonitor) {
                  if (isBookInitialized()) {
                    if (!appendDelta(delta)) {
                      disposables.add(asyncInitializeOrderBookSnapshot());
                    }
                  } else {
                    bufferDelta(delta);
                  }
                }
              })
          .filter(delta -> isBookInitialized())
          .map(delta -> getBook())
          .doFinally(() -> dispose())
          .subscribe(booksSubject);

      return booksSubject.hide();
    }

    @Override
    public void dispose() {
      if (!isDisposed()) {
        booksSubject.onComplete();
        disposables.dispose();
      }
    }

    private void disposeWithError(Throwable error) {
      if (!isDisposed()) {
        booksSubject.onError(error);
        disposables.dispose();
      }
    }

    @Override
    public boolean isDisposed() {
      return booksSubject.hasComplete() || booksSubject.hasThrowable();
    }

    private boolean isBookInitialized() {
      synchronized (bookIntegrityMonitor) {
        return book != null;
      }
    }

    private OrderBook getBook() {
      synchronized (bookIntegrityMonitor) {
        return book;
      }
    }

    private void bufferDelta(DepthBinanceFutureWebSocketTransaction delta) {
      synchronized (bookIntegrityMonitor) {
        deltasBuffer.add(delta);
      }
    }

    private Disposable asyncInitializeOrderBookSnapshot() {
      if (isBookInitialized()) {
        LOG.info("Orderbook snapshot for {} was initialized before. Re-syncing.", currencyPair);

        synchronized (bookIntegrityMonitor) {
          if (book != null) {
            book = null;
            deltasBuffer.clear();
            finalUpdateidPrev = 0;
          }
        }
      }

      return deltasObservable
          .firstOrError()
          .observeOn(bookSnapshotsScheduler)
          .flatMap(delta -> fetchSingleBinanceOrderBookUpdatedAfter(delta))
          .subscribe(
              binanceBook -> {
                final OrderBook convertedBook =
                    BinanceFutureMarketDataService.convertOrderBook(binanceBook, currencyPair);

                synchronized (bookIntegrityMonitor) {
                  book = convertedBook;
                  final List<DepthBinanceFutureWebSocketTransaction> applicableBookPatches =
                      deltasBuffer.stream()
                          .filter(delta -> delta.getLastUpdateId() >= binanceBook.lastUpdateId)
                          .collect(Collectors.toList());
                  // Drop any event where u is < lastUpdateId in the snapshot.
                  for (DepthBinanceFutureWebSocketTransaction d : deltasBuffer) {
                    LOG.trace(
                        "db uidp {} U {} u {} E {} pu {} T {}",
                        finalUpdateidPrev,
                        d.getFirstUpdateId(),
                        d.getLastUpdateId(),
                        d.getEventTime().getTime(),
                        d.getFinalUpdateid(),
                        Long.parseLong(d.getTransactionTime()));
                  }
                  deltasBuffer.clear();

                  // Update the book with all buffered deltas (as probably nobody would like to be
                  // notified with an already outdated snapshot).
                  LOG.trace("appli book Patches {}", applicableBookPatches.size());
                  boolean checkPass = false;
                  for (DepthBinanceFutureWebSocketTransaction applicableBookPatch :
                      applicableBookPatches) {
                    //                       The first processed event should have U <= lastUpdateId
                    // AND u >= lastUpdateId
                    // может быть проверка и лишняя, но пускай останется
                    if (!checkPass
                        && applicableBookPatch.getFirstUpdateId() <= binanceBook.lastUpdateId)
                      checkPass = true;
                    if (checkPass)
                      if (!appendDelta(applicableBookPatch)) {
                        disposables.add(asyncInitializeOrderBookSnapshot());
                      }
                  }
                }
              },
              error -> disposeWithError(error));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean appendDelta(DepthBinanceFutureWebSocketTransaction delta) {
      LOG.trace(
          "appendDelta before sync idPrev {} U {} u {} E {} pu {} T {}",
          finalUpdateidPrev,
          delta.getFirstUpdateId(),
          delta.getLastUpdateId(),
          delta.getEventTime().getTime(),
          delta.getFinalUpdateid(),
          Long.parseLong(delta.getTransactionTime()));

      synchronized (bookIntegrityMonitor) {
        LOG.trace(
            "appendDelta before check idPrev {} U {} u {} E {} pu {} T {}",
            finalUpdateidPrev,
            delta.getFirstUpdateId(),
            delta.getLastUpdateId(),
            delta.getEventTime().getTime(),
            delta.getFinalUpdateid(),
            Long.parseLong(delta.getTransactionTime()));
        if (finalUpdateidPrev != 0 && finalUpdateidPrev != delta.getFinalUpdateid()) {
          LOG.trace(
              "Orderbook snapshot for {} out of date (idPrev={}, U={}, u={}).",
              currencyPair,
              finalUpdateidPrev,
              delta.getFirstUpdateId(),
              delta.getLastUpdateId());
          return false;
        } else {
          finalUpdateidPrev = delta.getLastUpdateId();
          LOG.trace(
              "appendDelta before extractUpdates idPrev {} U {} u {} E {} pu {} T {}",
              finalUpdateidPrev,
              delta.getFirstUpdateId(),
              delta.getLastUpdateId(),
              delta.getEventTime().getTime(),
              delta.getFinalUpdateid(),
              Long.parseLong(delta.getTransactionTime()));
          // FIXME The underlying impl would be more optimal if LimitOrders were created directly.
          extractOrderBookUpdates(currencyPair, delta).forEach(update -> book.update(update));
        }
        return true;
      }
    }

    private Single<BinanceOrderbook> fetchSingleBinanceOrderBookUpdatedAfter(
        final DepthBinanceFutureWebSocketTransaction delta) {
      return Single.fromCallable(
              () -> {
                BinanceOrderbook snapshot;
                int attemptNum = 0;
                do {
                  attemptNum++;
                  // Get a snapshot.
                  LOG.info(
                      "Fetching initial orderbook snapshot for {}, attempt #{}",
                      currencyPair,
                      attemptNum);
                  snapshot = fetchBinanceOrderBook(currencyPair);

                  // Repeat while the snapshot is older than the provided delta (Why? To ensure we
                  // have deltas old enough to be able to apply a chain of updates to the snapshot
                  // in order to bump it to the current state). If any repeats will be indeed
                  // necessary, it's recommended to update the implementation, give some initial
                  // delay, after subscribed for deltas, before asked for the snapshot first time.
                  // Was not required at the time of writing, but exchange behaviour can change over
                  // time.
                  LOG.info(
                      "initial book snapshot for {} (lastUpdateId={}, U={}, u={}).",
                      currencyPair,
                      snapshot.lastUpdateId,
                      delta.getFirstUpdateId(),
                      delta.getLastUpdateId());
                } while (snapshot.lastUpdateId < delta.getFirstUpdateId());
                return snapshot;
              })
          .doOnError(
              error -> LOG.error("Failed to fetch initial order book for " + currencyPair, error));
    }

    private BinanceOrderbook fetchBinanceOrderBook(Instrument currencyPair)
        throws IOException, InterruptedException {
      try {
        onApiCall.run();
        fallbackOnApiCall.get().run();
        return marketDataService.getBinanceOrderbook(currencyPair, oderBookFetchLimitParameter);
      } catch (BinanceException e) {
        if (BinanceErrorAdapter.adapt(e) instanceof RateLimitExceededException) {
          if (fallenBack.compareAndSet(false, true)) {
            LOG.error(
                "API Rate limit was hit when fetching Binance order book snapshot. Provide a \n"
                    + "rate limiter. Apache Commons and Google Guava provide the TimedSemaphore\n"
                    + "and RateLimiter classes which are effective for this purpose. Example:\n"
                    + "\n"
                    + "  exchangeSpecification.setExchangeSpecificParametersItem(\n"
                    + "      info.bitrich.xchangestream.util.Events.BEFORE_API_CALL_HANDLER,\n"
                    + "      () -> rateLimiter.acquire())\n"
                    + "\n"
                    + "Pausing for 15sec and falling back to one call per three seconds, but you\n"
                    + "will get more optimal performance by handling your own rate limiting.");
            RateLimiter rateLimiter = RateLimiter.create(0.333);
            fallbackOnApiCall.set(rateLimiter::acquire);
            Thread.sleep(15000);
          }
        }
        throw e;
      }
    }
  }

  private Observable<DepthBinanceFutureWebSocketTransaction> rawOrderBookUpdates(
      Instrument currencyPair) {
    return service
        .subscribeChannel(
            channelFromCurrency(currencyPair, BinanceSubscriptionType.DEPTH.getType()))
        .map(
            it ->
                this.<DepthBinanceFutureWebSocketTransaction>readTransaction(
                    it, DEPTH_TYPE, "order book"))
        .map(BinanceWebsocketTransaction::getData)
        .filter(data -> data.getCurrencyPair().equals(currencyPair));
  }

  private Observable<OrderBook> createOrderBookObservable(Instrument currencyPair) {
    return new OrderBookSubscription(
            orderBookRawUpdatesSubscriptions.get(currencyPair), currencyPair)
        .connect();
  }

  private Observable<BinanceRawTrade> rawTradeStream(CurrencyPair currencyPair) {
    return service
        .subscribeChannel(
            channelFromCurrency(currencyPair, BinanceSubscriptionType.TRADE.getType()))
        .map(it -> this.<TradeBinanceWebsocketTransaction>readTransaction(it, TRADE_TYPE, "trade"))
        .filter(transaction -> transaction.getData().getCurrencyPair().equals(currencyPair))
        .map(transaction -> transaction.getData().getRawTrade());
  }

  /**
   * Force observable to execute its body, this way we get `BinanceStreamingService` to register the
   * observables emitter ready for our message arrivals.
   */
  private <T> Observable<T> triggerObservableBody(Observable<T> observable) {
    Consumer<T> NOOP = whatever -> {};
    observable.subscribe(NOOP);
    return observable;
  }

  private <T> BinanceWebsocketTransaction<T> readTransaction(
      JsonNode node, JavaType type, String transactionType) {
    try {
      return mapper.readValue(mapper.treeAsTokens(node), type);
    } catch (IOException e) {
      throw new ExchangeException(
          String.format("Unable to parse %s transaction", transactionType), e);
    }
  }

  private Stream<OrderBookUpdate> extractOrderBookUpdates(
      Instrument currencyPair, DepthBinanceFutureWebSocketTransaction depthTransaction) {
    BinanceOrderbook orderBookDiff = depthTransaction.getOrderBook();
    DepthBinanceFutureWebSocketTransaction delta = depthTransaction;
    LOG.trace(
        "dt U {} u {} E {} pu {} T {}",
        delta.getFirstUpdateId(),
        delta.getLastUpdateId(),
        delta.getEventTime().getTime(),
        delta.getFinalUpdateid(),
        Long.parseLong(delta.getTransactionTime()));

    Stream<OrderBookUpdate> bidStream =
        orderBookDiff.bids.entrySet().stream()
            .map(
                entry ->
                    new OrderBookUpdate(
                        OrderType.BID,
                        entry.getValue(),
                        currencyPair,
                        entry.getKey(),
                        depthTransaction.getEventTime(),
                        entry.getValue()));

    Stream<OrderBookUpdate> askStream =
        orderBookDiff.asks.entrySet().stream()
            .map(
                entry ->
                    new OrderBookUpdate(
                        OrderType.ASK,
                        entry.getValue(),
                        currencyPair,
                        entry.getKey(),
                        depthTransaction.getEventTime(),
                        entry.getValue()));

    return Stream.concat(bidStream, askStream);
  }

  private <T> Observable<T> observableFromStream(Stream<T> stream) {
    return Observable.create(
        emitter -> {
          stream.forEach(emitter::onNext);
          emitter.onComplete();
        });
  }

  private static JavaType getTickerType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction>>() {});
  }

  private static JavaType getBookTickerType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<
                BinanceWebsocketTransaction<BookTickerBinanceWebSocketTransaction>>() {});
  }

  private static JavaType getTradeType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<BinanceWebsocketTransaction<TradeBinanceWebsocketTransaction>>() {});
  }

  private static JavaType getDepthType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<
                BinanceWebsocketTransaction<DepthBinanceFutureWebSocketTransaction>>() {});
  }

  private static JavaType getKlineType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<BinanceWebsocketTransaction<KlineBinanceWebSocketTransaction>>() {});
  }
}
