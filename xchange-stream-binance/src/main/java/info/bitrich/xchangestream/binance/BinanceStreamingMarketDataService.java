package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import info.bitrich.xchangestream.binance.dto.BinanceRawTrade;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.BookTickerBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.KlineBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.TickerBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.TradeBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.exceptions.UpFrontSubscriptionRequiredException;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceBookTicker;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
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

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static info.bitrich.xchangestream.binance.BinanceSubscriptionType.KLINE;
import static info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper.getObjectMapper;

public class BinanceStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceStreamingMarketDataService.class);

  private static final JavaType TICKER_TYPE = getTickerType();
  private static final JavaType BOOK_TICKER_TYPE = getBookTickerType();
  private static final JavaType TRADE_TYPE = getTradeType();
  private static final JavaType DEPTH_TYPE = getDepthType();
  private static final JavaType KLINE_TYPE = getKlineType();

  private final BinanceStreamingService service;
  private final String orderBookUpdateFrequencyParameter;
  private final boolean realtimeOrderBookTicker;
  private final int oderBookFetchLimitParameter;

  private final Map<Instrument, Observable<BinanceTicker24h>> tickerSubscriptions;
  private final Map<Instrument, Observable<BinanceBookTicker>> bookTickerSubscriptions;
  private final Map<Instrument, Observable<OrderBook>> orderbookSubscriptions;
  private final Map<Instrument, Observable<BinanceRawTrade>> tradeSubscriptions;
  private final Map<Instrument, Observable<OrderBookUpdate>> orderBookUpdatesSubscriptions;
  private final Map<Instrument, Map<KlineInterval, Observable<BinanceKline>>> klineSubscriptions;
  private final Map<Instrument, Observable<DepthBinanceWebSocketTransaction>>
      orderBookRawUpdatesSubscriptions;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final BinanceMarketDataService marketDataService;
  private final Runnable onApiCall;

  private final AtomicBoolean fallenBack = new AtomicBoolean();
  private final AtomicReference<Runnable> fallbackOnApiCall = new AtomicReference<>(() -> {});

  public BinanceStreamingMarketDataService(
      BinanceStreamingService service,
      BinanceMarketDataService marketDataService,
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
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getOrderBook().contains(currencyPair)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return orderbookSubscriptions.computeIfAbsent(currencyPair, this::initOrderBookIfAbsent);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    if (realtimeOrderBookTicker) {
      return getRawBookTicker(currencyPair).map(raw-> raw.toTicker(false));
    }
    return getRawTicker(currencyPair).map(raw-> raw.toTicker(false));
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return getRawTrades(currencyPair)
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

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    if (!service.isLiveSubscriptionEnabled()
            && !service.getProductSubscription().getOrderBook().contains(instrument)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return orderbookSubscriptions.computeIfAbsent(instrument, this::initOrderBookIfAbsent);
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    if (realtimeOrderBookTicker) {
      return getRawBookTicker(instrument).map(raw-> raw.toTicker(instrument instanceof FuturesContract));
    }
    return getRawTicker(instrument).map(raw-> raw.toTicker(instrument instanceof FuturesContract));
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    return getRawTrades(instrument)
            .map(
                    rawTrade ->
                            new Trade.Builder()
                                    .type(BinanceAdapters.convertType(rawTrade.isBuyerMarketMaker()))
                                    .originalAmount(rawTrade.getQuantity())
                                    .instrument(instrument)
                                    .price(rawTrade.getPrice())
                                    .makerOrderId(getMakerOrderId(rawTrade))
                                    .takerOrderId(getTakerOrderId(rawTrade))
                                    .timestamp(new Date(rawTrade.getTimestamp()))
                                    .id(String.valueOf(rawTrade.getTradeId()))
                                    .build());
  }

  private Observable<OrderBook> initOrderBookIfAbsent(Instrument instrument) {
    orderBookRawUpdatesSubscriptions.computeIfAbsent(
        instrument, s -> triggerObservableBody(rawOrderBookUpdates(instrument)));
    return createOrderBookObservable(instrument);
  }

  public Observable<BinanceTicker24h> getRawTicker(Instrument instrument) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTicker().contains(instrument)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return tickerSubscriptions.computeIfAbsent(
        instrument, s -> triggerObservableBody(rawTickerStream(instrument)).share());
  }

  public Observable<BinanceBookTicker> getRawBookTicker(Instrument instrument) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTicker().contains(instrument)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return bookTickerSubscriptions.computeIfAbsent(
        instrument, s -> triggerObservableBody(rawBookTickerStream(instrument)).share());
  }

  public Observable<BinanceRawTrade> getRawTrades(Instrument instrument) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getProductSubscription().getTrades().contains(instrument)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return tradeSubscriptions.computeIfAbsent(
        instrument, s -> triggerObservableBody(rawTradeStream(instrument)).share());
  }

  public Observable<BinanceKline> getKlines(CurrencyPair currencyPair, KlineInterval interval) {
    if (!service.isLiveSubscriptionEnabled()
        && !service.getKlineSubscription().contains(currencyPair, interval)) {
      throw new UpFrontSubscriptionRequiredException();
    }
    return klineSubscriptions.compute(
        currencyPair, (c, v) -> {
          Map<KlineInterval, Observable<BinanceKline>> intervalMap = createMapIfNull(v);

          intervalMap.computeIfAbsent(interval, i -> triggerObservableBody(klinesStream(currencyPair, interval)).share());

          return intervalMap;
        }).get(interval);

  }

  private static <K, V> Map<K, V> createMapIfNull(Map<K, V> map) {
    return map == null ? new ConcurrentHashMap<>() : map;
  }

  private Observable<BinanceKline> klinesStream(Instrument instrument, KlineInterval interval) {
    return service
        .subscribeChannel(
            getChannelPrefix(instrument) + "@" + KLINE.getType() + "_" + interval.code())
        .map(it -> this.<KlineBinanceWebSocketTransaction>readTransaction(it, KLINE_TYPE, "kline").getData().toBinanceKline(instrument instanceof FuturesContract))
        .filter(binanceKline -> binanceKline.getInstrument().equals(instrument)
            && binanceKline.getInterval().equals(interval));
  }

  /**
   * Api to get binance incremental order book updates. As binance websocket provides only api to
   * get incremental updates {@link #getOrderBook(CurrencyPair, Object...)} have to build book from
   * rest and websocket which leads to delay before the order book will be received by subscriber.
   * This api provides the ability to start receiving updates immediately. It is allowed to
   * subscribe to this api and {@link #getOrderBook(CurrencyPair, Object...)} at the same time.
   */
  public Observable<OrderBookUpdate> getOrderBookUpdates(
      CurrencyPair currencyPair) {
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

  private String getChannelPrefix(Instrument instrument) {
    return (instrument instanceof FuturesContract)
            ? ((FuturesContract) instrument).getCurrencyPair().toString().replace("/","").toLowerCase()
            : instrument.toString().replace("/","").toLowerCase();
  }

  /**
   * Registers subscriptions with the streaming service for the given products.
   *
   * <p>As we receive messages as soon as the connection is open, we need to register subscribers to
   * handle these before the first messages arrive.
   */
  public void openSubscriptions(ProductSubscription productSubscription, KlineSubscription klineSubscription) {
    klineSubscription.getKlines().forEach((this::initKlineSubscription));
    productSubscription.getTicker().forEach(this::initTickerSubscription);
    productSubscription.getOrderBook().forEach(this::initRawOrderBookUpdatesSubscription);
    productSubscription.getTrades().forEach(this::initTradeSubscription);
  }

  private void initKlineSubscription(Instrument currencyPair, Set<KlineInterval> klineIntervals) {
    klineSubscriptions.compute(currencyPair, (c, v) -> {
      Map<KlineInterval, Observable<BinanceKline>> intervalMap = createMapIfNull(v);
      klineIntervals.forEach(interval -> intervalMap.put(interval, triggerObservableBody(klinesStream(currencyPair, interval))));
      return intervalMap;
    });
  }

  /**
   * Live Unsubscription that should be called in the Observable.doOnDispose(). This is required to
   * stop receiving data from the stream. This method also clear the subscription from the
   * appropriate map.
   */
  public void unsubscribe(CurrencyPair currencyPair, BinanceSubscriptionType subscriptionType) {
    if (subscriptionType == KLINE) {
      klineSubscriptions.computeIfPresent(currencyPair, (k, intervalMap) -> {
        intervalMap.keySet()
            .forEach(klineInterval -> unsubscribeKline(currencyPair, klineInterval));
        return null;
      });
    } else {
      unsubscribe(currencyPair, subscriptionType, null);
    }
  }

  public void unsubscribeKline(CurrencyPair currencyPair, KlineInterval klineInterval) {
    unsubscribe(currencyPair, KLINE, klineInterval);
  }

  private void unsubscribe(CurrencyPair currencyPair, BinanceSubscriptionType subscriptionType, KlineInterval klineInterval) {

    if (!service.isLiveSubscriptionEnabled()) {
      throw new UnsupportedOperationException(
          "Unsubscribe not supported for Binance when live Subscription/Unsubscription is disabled. "
              + "Call BinanceStreamingExchange.enableLiveSubscription() to active it");
    }
    String channelId =
        getChannelId(currencyPair, subscriptionType, klineInterval);
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
        klineSubscriptions.computeIfPresent(currencyPair, (k, intervalMap) -> {
          intervalMap.remove(klineInterval);
          return intervalMap;
        });
      default:
        throw new IllegalArgumentException("Subscription type not supported to unsubscribe from stream");
    }
  }

  private String getChannelId(CurrencyPair currencyPair, BinanceSubscriptionType subscriptionType, KlineInterval klineInterval) {
    return getChannelPrefix(currencyPair)
        + "@"
        + subscriptionType.getType()
        + (klineInterval != null ? "_" + klineInterval.code() : "");
  }

  private void initTradeSubscription(Instrument currencyPair) {
    tradeSubscriptions.put(
        currencyPair, triggerObservableBody(rawTradeStream(currencyPair)).share());
  }

  private void initTickerSubscription(Instrument currencyPair) {
    if (realtimeOrderBookTicker) {
      bookTickerSubscriptions.put(
          currencyPair, triggerObservableBody(rawBookTickerStream(currencyPair)).share());
    } else {
      tickerSubscriptions.put(
          currencyPair, triggerObservableBody(rawTickerStream(currencyPair)).share());
    }
  }

  private void initRawOrderBookUpdatesSubscription(Instrument currencyPair) {
    orderBookRawUpdatesSubscriptions.put(
        currencyPair, triggerObservableBody(rawOrderBookUpdates(currencyPair)));
  }

  private Observable<BinanceTicker24h> rawTickerStream(Instrument instrument) {
    return service
        .subscribeChannel(
            channelFromCurrency(instrument, BinanceSubscriptionType.TICKER.getType()))
        .map(
            it ->
                this.<TickerBinanceWebsocketTransaction>readTransaction(it, TICKER_TYPE, "ticker"))
        .filter(transaction -> BinanceAdapters.adaptSymbol(transaction.getData().getSymbol(), instrument instanceof FuturesContract).equals(instrument))
        .map(transaction -> transaction.getData().getTicker());
  }

  private Observable<BinanceBookTicker> rawBookTickerStream(Instrument instrument) {
    return service
        .subscribeChannel(
            channelFromCurrency(instrument, BinanceSubscriptionType.BOOK_TICKER.getType()))
        .map(
            it ->
                this.<BookTickerBinanceWebSocketTransaction>readTransaction(
                    it, BOOK_TICKER_TYPE, "book ticker"))
        .filter(transaction -> BinanceAdapters.adaptSymbol(transaction.getData().getTicker().getSymbol(), instrument instanceof FuturesContract).equals(instrument))
        .map(transaction -> transaction.getData().getTicker());
  }

  private final class OrderbookSubscription {
    final Observable<DepthBinanceWebSocketTransaction> stream;
    final AtomicLong lastUpdateId = new AtomicLong();
    final AtomicLong snapshotLastUpdateId = new AtomicLong();
    OrderBook orderBook;

    private OrderbookSubscription(Observable<DepthBinanceWebSocketTransaction> stream) {
      this.stream = stream;
    }

    void invalidateSnapshot() {
      snapshotLastUpdateId.set(0);
    }

    void initSnapshotIfInvalid(Instrument currencyPair) {
      if (snapshotLastUpdateId.get() != 0) return;
      try {
        LOG.info("Fetching initial orderbook snapshot for {} ", currencyPair);
        onApiCall.run();
        fallbackOnApiCall.get().run();
        BinanceOrderbook book = fetchBinanceOrderBook(currencyPair);
        snapshotLastUpdateId.set(book.lastUpdateId);
        lastUpdateId.set(book.lastUpdateId);
        orderBook = BinanceMarketDataService.convertOrderBook(book, currencyPair);
      } catch (Exception e) {
        LOG.error("Failed to fetch initial order book for " + currencyPair, e);
        snapshotLastUpdateId.set(0);
        lastUpdateId.set(0);
        orderBook = null;
      }
    }

    private BinanceOrderbook fetchBinanceOrderBook(Instrument currencyPair)
        throws IOException, InterruptedException {
      try {
        return marketDataService.getBinanceOrderbookAllProducts(currencyPair, oderBookFetchLimitParameter);
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

  private Observable<DepthBinanceWebSocketTransaction> rawOrderBookUpdates(
          Instrument instrument) {
    return service
        .subscribeChannel(
            channelFromCurrency(instrument, BinanceSubscriptionType.DEPTH.getType()))
        .map(
            it ->
                this.<DepthBinanceWebSocketTransaction>readTransaction(
                    it, DEPTH_TYPE, "order book"))
        .map(BinanceWebsocketTransaction::getData)
        .filter(data -> BinanceAdapters.adaptSymbol(data.getSymbol(), instrument instanceof FuturesContract).equals(instrument));
  }

  private Observable<OrderBook> createOrderBookObservable(Instrument currencyPair) {
    // 1. Open a stream to wss://stream.binance.com:9443/ws/bnbbtc@depth
    // 2. Buffer the events you receive from the stream.
    OrderbookSubscription subscription =
        new OrderbookSubscription(orderBookRawUpdatesSubscriptions.get(currencyPair));

    return subscription
        .stream

        // 3. Get a depth snapshot from
        // https://www.binance.com/api/v1/depth?symbol=BNBBTC&limit=1000
        // (we do this if we don't already have one or we've invalidated a previous one)
        .doOnNext(transaction -> subscription.initSnapshotIfInvalid(currencyPair))

        // If we failed, don't return anything. Just keep trying until it works
        .filter(transaction -> subscription.snapshotLastUpdateId.get() > 0L)

        // 4. Drop any event where u is <= lastUpdateId in the snapshot
        .filter(depth -> depth.getLastUpdateId() > subscription.snapshotLastUpdateId.get())

        // 5. The first processed should have U <= lastUpdateId+1 AND u >= lastUpdateId+1, and
        // subsequent events would
        // normally have u == lastUpdateId + 1 which is stricter version of the above - let's be
        // more relaxed
        // each update has absolute numbers so even if there's an overlap it does no harm
        .filter(
            depth -> {
              long lastUpdateId = subscription.lastUpdateId.get();
              boolean result;
              if (lastUpdateId == 0L) {
                result = true;
              } else {
                result =
                    depth.getFirstUpdateId() <= lastUpdateId + 1
                        && depth.getLastUpdateId() >= lastUpdateId + 1;
              }
              if (result) {
                subscription.lastUpdateId.set(depth.getLastUpdateId());
              } else {
                // If not, we re-sync.  This will commonly occur a few times when starting up, since
                // given update ids 1,2,3,4,5,6,7,8,9, Binance may sometimes return a snapshot
                // as of 5, but update events covering 1-3, 4-6 and 7-9.  We can't apply the 4-6
                // update event without double-counting 5, and we can't apply the 7-9 update without
                // missing 6.  The only thing we can do is to keep requesting a fresh snapshot until
                // we get to a situation where the snapshot and an update event precisely line up.
                LOG.info(
                    "Orderbook snapshot for {} out of date (last={}, U={}, u={}). This is normal. Re-syncing.",
                    currencyPair,
                    lastUpdateId,
                    depth.getFirstUpdateId(),
                    depth.getLastUpdateId());
                subscription.invalidateSnapshot();
              }
              return result;
            })

        // 7. The data in each event is the absolute quantity for a price level
        // 8. If the quantity is 0, remove the price level
        // 9. Receiving an event that removes a price level that is not in your local order book can
        // happen and is normal.
        .map(
            depth -> {
              extractOrderBookUpdates(currencyPair, depth)
                  .forEach(it -> subscription.orderBook.update(it));
              return subscription.orderBook;
            })
        .share();
  }

  private Observable<BinanceRawTrade> rawTradeStream(Instrument instrument) {
    return service
        .subscribeChannel(
            channelFromCurrency(instrument, BinanceSubscriptionType.TRADE.getType()))
        .map(it -> this.<TradeBinanceWebsocketTransaction>readTransaction(it, TRADE_TYPE, "trade"))
        .filter(transaction -> BinanceAdapters.adaptSymbol(transaction.getData().getSymbol(), instrument instanceof FuturesContract).equals(instrument))
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
          Instrument currencyPair, DepthBinanceWebSocketTransaction depthTransaction) {
    BinanceOrderbook orderBookDiff = depthTransaction.getOrderBook();

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
            new TypeReference<BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction>>() {});
  }

  private static JavaType getKlineType() {
    return getObjectMapper()
        .getTypeFactory()
        .constructType(
            new TypeReference<BinanceWebsocketTransaction<KlineBinanceWebSocketTransaction>>() {});
  }
}
