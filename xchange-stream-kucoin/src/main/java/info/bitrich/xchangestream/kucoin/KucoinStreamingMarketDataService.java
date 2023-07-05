package info.bitrich.xchangestream.kucoin;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kucoin.dto.KucoinOrderBookEventData;
import info.bitrich.xchangestream.kucoin.dto.KucoinOrderBookEvent;
import info.bitrich.xchangestream.kucoin.dto.KucoinTickerEvent;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.kucoin.KucoinAdapters;
import org.knowm.xchange.kucoin.KucoinMarketDataService;
import org.knowm.xchange.kucoin.dto.response.OrderBookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class KucoinStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger logger =
      LoggerFactory.getLogger(KucoinStreamingMarketDataService.class);

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();
  private final Map<CurrencyPair, Observable<OrderBook>> orderbookSubscriptions = new ConcurrentHashMap<>();
  private final Map<CurrencyPair, Observable<KucoinOrderBookEventData>> orderBookRawUpdatesSubscriptions = new ConcurrentHashMap<>();

  private final KucoinStreamingService service;
  private final KucoinMarketDataService marketDataService;
  private final Runnable onApiCall;

  public KucoinStreamingMarketDataService(
          KucoinStreamingService service,
          KucoinMarketDataService marketDataService,
          Runnable onApiCall
  ) {
    this.service = service;
    this.marketDataService = marketDataService;
    this.onApiCall = onApiCall;
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    String channelName = "/market/ticker:" + KucoinAdapters.adaptCurrencyPair(currencyPair);
    return service
        .subscribeChannel(channelName)
        .doOnError(ex -> logger.warn("encountered error while subscribing to channel " + channelName, ex))
        .map(node -> mapper.treeToValue(node, KucoinTickerEvent.class))
        .map(KucoinTickerEvent::getTicker);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return orderbookSubscriptions.computeIfAbsent(currencyPair, this::initOrderBookIfAbsent);
  }

  private Observable<OrderBook> initOrderBookIfAbsent(CurrencyPair currencyPair) {
    orderBookRawUpdatesSubscriptions.computeIfAbsent(currencyPair,
            s -> triggerObservableBody(rawOrderBookUpdates(s)));
    return createOrderBookObservable(currencyPair);
  }

  private Observable<KucoinOrderBookEventData> rawOrderBookUpdates(CurrencyPair currencyPair) {
    String channelName = "/market/level2:" + KucoinAdapters.adaptCurrencyPair(currencyPair);

    return service
            .subscribeChannel(channelName)
            .doOnError(ex -> logger.warn("encountered error while subscribing to channel " + channelName, ex))
            .map(it -> mapper.treeToValue(it, KucoinOrderBookEvent.class))
            .map(e -> e.data);
  }


  private Observable<OrderBook> createOrderBookObservable(CurrencyPair currencyPair) {
    // 1. Open a stream
    // 2. Buffer the events you receive from the stream.
    OrderbookSubscription subscription =
            new OrderbookSubscription(orderBookRawUpdatesSubscriptions.get(currencyPair));

    return subscription
            .stream
            // 3. Get a depth snapshot
            // (we do this if we don't already have one or we've invalidated a previous one)
            .doOnNext(transaction -> subscription.initSnapshotIfInvalid(currencyPair))

            .doOnError(ex -> logger.warn("encountered error while processing order book event", ex))

            // If we failed, don't return anything. Just keep trying until it works
            .filter(transaction -> subscription.snapshotLastUpdateId.get() > 0L)

            // 4. Drop any event where u is <= lastUpdateId in the snapshot
            .filter(depth -> depth.sequenceEnd > subscription.snapshotLastUpdateId.get())

            // 5. The first processed should have U <= lastUpdateId+1 AND u >= lastUpdateId+1, and
            // subsequent events would
            // normally have u == lastUpdateId + 1 which is stricter version of the above - let's be
            // more relaxed
            // each update has absolute numbers so even if there's an overlap it does no harm
            .filter(
                    depth -> {
                      long lastUpdateId = subscription.lastUpdateId.get();
                      boolean result = lastUpdateId == 0L ||
                              (depth.sequenceStart <= lastUpdateId + 1 && depth.sequenceEnd >= lastUpdateId + 1);
                      if (result) {
                        subscription.lastUpdateId.set(depth.sequenceEnd);
                      } else {
                        // If not, we re-sync
                        logger.info(
                                "Orderbook snapshot for {} out of date (last={}, U={}, u={}). This is normal. Re-syncing.",
                                currencyPair,
                                lastUpdateId,
                                depth.sequenceStart,
                                depth.sequenceEnd);
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
                      depth.update(currencyPair, subscription.orderBook);
                      return subscription.orderBook;
                    })
            .share();
  }


  private <T> Observable<T> triggerObservableBody(Observable<T> observable) {
    Consumer<T> NOOP = whatever -> {};
    observable.subscribe(NOOP);
    return observable;
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException();
  }

  private final class OrderbookSubscription {
    final Observable<KucoinOrderBookEventData> stream;
    final AtomicLong lastUpdateId = new AtomicLong();
    final AtomicLong snapshotLastUpdateId = new AtomicLong();
    OrderBook orderBook;

    private OrderbookSubscription(Observable<KucoinOrderBookEventData> stream) {
      this.stream = stream;
    }

    void invalidateSnapshot() {
      snapshotLastUpdateId.set(0);
    }

    void initSnapshotIfInvalid(CurrencyPair currencyPair) {
      if (snapshotLastUpdateId.get() != 0) return;
      try {
        logger.info("Fetching initial orderbook snapshot for {} ", currencyPair);
        onApiCall.run();
        OrderBookResponse book = marketDataService.getKucoinOrderBookFull(currencyPair);
        lastUpdateId.set(Long.parseLong(book.getSequence()));
        snapshotLastUpdateId.set(lastUpdateId.get());
        orderBook = KucoinAdapters.adaptOrderBook(currencyPair, book);
      } catch (Exception e) {
        logger.error("Failed to fetch initial order book for " + currencyPair, e);
        snapshotLastUpdateId.set(0);
        lastUpdateId.set(0);
        orderBook = null;
      }
    }
  }
}
