package info.bitrich.xchangestream.binance;

import static info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper.getObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import info.bitrich.xchangestream.binance.dto.BinanceRawTrade;
import info.bitrich.xchangestream.binance.dto.BinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.DepthBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.TickerBinanceWebsocketTransaction;
import info.bitrich.xchangestream.binance.dto.TradeBinanceWebsocketTransaction;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceErrorAdapter;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.RateLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceStreamingMarketDataService implements StreamingMarketDataService {
  private static final Logger LOG =
      LoggerFactory.getLogger(BinanceStreamingMarketDataService.class);

  private static final JavaType TICKER_TYPE =
      getObjectMapper()
          .getTypeFactory()
          .constructType(
              new TypeReference<
                  BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction>>() {});
  private static final JavaType TRADE_TYPE =
      getObjectMapper()
          .getTypeFactory()
          .constructType(
              new TypeReference<
                  BinanceWebsocketTransaction<TradeBinanceWebsocketTransaction>>() {});
  private static final JavaType DEPTH_TYPE =
      getObjectMapper()
          .getTypeFactory()
          .constructType(
              new TypeReference<
                  BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction>>() {});

  private final BinanceStreamingService service;
  private final String orderBookUpdateFrequencyParameter;

  private final Map<CurrencyPair, OrderbookSubscription> orderbooks = new HashMap<>();
  private final Map<CurrencyPair, Observable<BinanceTicker24h>> tickerSubscriptions =
      new HashMap<>();
  private final Map<CurrencyPair, Observable<OrderBook>> orderbookSubscriptions = new HashMap<>();
  private final Map<CurrencyPair, Observable<BinanceRawTrade>> tradeSubscriptions = new HashMap<>();

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final BinanceMarketDataService marketDataService;
  private final Runnable onApiCall;

  private final AtomicBoolean fallenBack = new AtomicBoolean();
  private final AtomicReference<Runnable> fallbackOnApiCall = new AtomicReference<>(() -> {});

  public BinanceStreamingMarketDataService(
      BinanceStreamingService service,
      BinanceMarketDataService marketDataService,
      Runnable onApiCall,
      final String orderBookUpdateFrequencyParameter) {
    this.service = service;
    this.orderBookUpdateFrequencyParameter = orderBookUpdateFrequencyParameter;
    this.marketDataService = marketDataService;
    this.onApiCall = onApiCall;
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!service.getProductSubscription().getOrderBook().contains(currencyPair)) {
      throw new UnsupportedOperationException(
          "Binance exchange only supports up front subscriptions - subscribe at connect time");
    }
    return orderbookSubscriptions.get(currencyPair);
  }

  public Observable<BinanceTicker24h> getRawTicker(CurrencyPair currencyPair, Object... args) {
    if (!service.getProductSubscription().getTicker().contains(currencyPair)) {
      throw new UnsupportedOperationException(
          "Binance exchange only supports up front subscriptions - subscribe at connect time");
    }
    return tickerSubscriptions.get(currencyPair);
  }

  public Observable<BinanceRawTrade> getRawTrades(CurrencyPair currencyPair, Object... args) {
    if (!service.getProductSubscription().getTrades().contains(currencyPair)) {
      throw new UnsupportedOperationException(
          "Binance exchange only supports up front subscriptions - subscribe at connect time");
    }
    return tradeSubscriptions.get(currencyPair);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
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
                    .currencyPair(currencyPair)
                    .price(rawTrade.getPrice())
                    .timestamp(new Date(rawTrade.getTimestamp()))
                    .id(String.valueOf(rawTrade.getTradeId()))
                    .build());
  }

  private String channelFromCurrency(CurrencyPair currencyPair, String subscriptionType) {
    String currency = String.join("", currencyPair.toString().split("/")).toLowerCase();
    String currencyChannel = currency + "@" + subscriptionType;

    if ("depth".equals(subscriptionType)) {
      return currencyChannel + orderBookUpdateFrequencyParameter;
    } else {
      return currencyChannel;
    }
  }

  /**
   * Registers subsriptions with the streaming service for the given products.
   *
   * <p>As we receive messages as soon as the connection is open, we need to register subscribers to
   * handle these before the first messages arrive.
   */
  public void openSubscriptions(ProductSubscription productSubscription) {
    productSubscription
        .getTicker()
        .forEach(
            currencyPair ->
                tickerSubscriptions.put(
                    currencyPair, triggerObservableBody(rawTickerStream(currencyPair).share())));
    productSubscription
        .getOrderBook()
        .forEach(
            currencyPair ->
                orderbookSubscriptions.put(
                    currencyPair, triggerObservableBody(orderBookStream(currencyPair).share())));
    productSubscription
        .getTrades()
        .forEach(
            currencyPair ->
                tradeSubscriptions.put(
                    currencyPair, triggerObservableBody(rawTradeStream(currencyPair).share())));
  }

  private Observable<BinanceTicker24h> rawTickerStream(CurrencyPair currencyPair) {
    return service
        .subscribeChannel(channelFromCurrency(currencyPair, "ticker"))
        .map(this::tickerTransaction)
        .filter(transaction -> transaction.getData().getCurrencyPair().equals(currencyPair))
        .map(transaction -> transaction.getData().getTicker());
  }

  private final class OrderbookSubscription {
    long snapshotlastUpdateId;
    AtomicLong lastUpdateId = new AtomicLong(0L);
    OrderBook orderBook;
    Observable<BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction>> stream;

    void invalidateSnapshot() {
      snapshotlastUpdateId = 0L;
    }

    void initSnapshotIfInvalid(CurrencyPair currencyPair) {
      if (snapshotlastUpdateId != 0L) return;
      try {
        LOG.info("Fetching initial orderbook snapshot for {} ", currencyPair);
        onApiCall.run();
        fallbackOnApiCall.get().run();
        BinanceOrderbook book = fetchBinanceOrderBook(currencyPair);
        snapshotlastUpdateId = book.lastUpdateId;
        lastUpdateId.set(book.lastUpdateId);
        orderBook = BinanceMarketDataService.convertOrderBook(book, currencyPair);
      } catch (Exception e) {
        LOG.error("Failed to fetch initial order book for " + currencyPair, e);
        snapshotlastUpdateId = 0L;
        lastUpdateId.set(0L);
        orderBook = null;
      }
    }

    private BinanceOrderbook fetchBinanceOrderBook(CurrencyPair currencyPair)
        throws IOException, InterruptedException {
      try {
        return marketDataService.getBinanceOrderbook(currencyPair, 1000);
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

  private OrderbookSubscription connectOrderBook(CurrencyPair currencyPair) {
    OrderbookSubscription subscription = new OrderbookSubscription();

    // 1. Open a stream to wss://stream.binance.com:9443/ws/bnbbtc@depth
    // 2. Buffer the events you receive from the stream.

    subscription.stream =
        service
            .subscribeChannel(channelFromCurrency(currencyPair, "depth"))
            .map(this::depthTransaction)
            .filter(transaction -> transaction.getData().getCurrencyPair().equals(currencyPair));
    return subscription;
  }

  private Observable<OrderBook> orderBookStream(CurrencyPair currencyPair) {
    OrderbookSubscription subscription =
        orderbooks.computeIfAbsent(currencyPair, this::connectOrderBook);

    return subscription
        .stream

        // 3. Get a depth snapshot from
        // https://www.binance.com/api/v1/depth?symbol=BNBBTC&limit=1000
        // (we do this if we don't already have one or we've invalidated a previous one)
        .doOnNext(transaction -> subscription.initSnapshotIfInvalid(currencyPair))

        // If we failed, don't return anything. Just keep trying until it works
        .filter(transaction -> subscription.snapshotlastUpdateId > 0L)
        .map(BinanceWebsocketTransaction::getData)

        // 4. Drop any event where u is <= lastUpdateId in the snapshot
        .filter(depth -> depth.getLastUpdateId() > subscription.snapshotlastUpdateId)

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
              BinanceOrderbook ob = depth.getOrderBook();
              ob.bids.forEach(
                  (key, value) ->
                      subscription.orderBook.update(
                          new OrderBookUpdate(
                              OrderType.BID,
                              null,
                              currencyPair,
                              key,
                              depth.getEventTime(),
                              value)));
              ob.asks.forEach(
                  (key, value) ->
                      subscription.orderBook.update(
                          new OrderBookUpdate(
                              OrderType.ASK,
                              null,
                              currencyPair,
                              key,
                              depth.getEventTime(),
                              value)));
              return subscription.orderBook;
            });
  }

  private Observable<BinanceRawTrade> rawTradeStream(CurrencyPair currencyPair) {
    return service
        .subscribeChannel(channelFromCurrency(currencyPair, "trade"))
        .map(this::tradeTransaction)
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

  private BinanceWebsocketTransaction<TickerBinanceWebsocketTransaction> tickerTransaction(
      JsonNode node) {
    try {
      return mapper.readValue(mapper.treeAsTokens(node), TICKER_TYPE);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse ticker transaction", e);
    }
  }

  private BinanceWebsocketTransaction<DepthBinanceWebSocketTransaction> depthTransaction(
      JsonNode node) {
    try {
      return mapper.readValue(mapper.treeAsTokens(node), DEPTH_TYPE);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse order book transaction", e);
    }
  }

  private BinanceWebsocketTransaction<TradeBinanceWebsocketTransaction> tradeTransaction(
      JsonNode node) {
    try {
      return mapper.readValue(mapper.treeAsTokens(node), TRADE_TYPE);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse trade transaction", e);
    }
  }
}
