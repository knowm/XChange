package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.node.ArrayNode;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kraken.dto.KrakenStreamingOhlc;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenSubscriptionName;
import io.reactivex.rxjava3.core.Observable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang3.ObjectUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author makarid, pchertalev
 */
public class KrakenStreamingMarketDataService implements StreamingMarketDataService {

  private static final Logger LOG = LoggerFactory.getLogger(KrakenStreamingMarketDataService.class);

  private static final int MIN_DATA_ARRAY_SIZE = 4;

  public static final String KRAKEN_CHANNEL_DELIMITER = "-";

  private final KrakenStreamingService service;
  private final boolean spreadForTicker;

  /**
   * One flag per live order book subscription, grouped by Kraken book channel (pair and depth).
   * All subscriptions on the same channel share one entry, whether they came from the same
   * {@link #getOrderBook} call or from separate calls, because they share one Kraken subscription.
   */
  private final Map<String, Set<AtomicBoolean>> activeBooksByChannel = new ConcurrentHashMap<>();

  public KrakenStreamingMarketDataService(KrakenStreamingService service, boolean spreadForTicker) {
    this.service = service;
    this.spreadForTicker = spreadForTicker;
  }

  /**
   * Each subscription to the returned observable keeps its own book. A subscription that joins
   * while another one already holds a book on the same Kraken channel, from this call or from an
   * earlier {@code getOrderBook} call for the same pair and depth, asks Kraken for a fresh
   * snapshot by resubscribing the channel; the existing subscriptions replace their books with
   * that snapshot, so every late joiner costs them one snapshot.
   */
  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(KrakenSubscriptionName.book, currencyPair);
    int depth =
        ObjectUtils.defaultIfNull(
            KrakenStreamingService.parseOrderBookSize(args),
            KrakenStreamingService.ORDER_BOOK_SIZE_DEFAULT);
    Observable<ArrayNode> channel = subscribe(channelName, MIN_DATA_ARRAY_SIZE, args);
    Set<AtomicBoolean> activeBooks =
        activeBooksByChannel.computeIfAbsent(
            channelName + KRAKEN_CHANNEL_DELIMITER + depth, k -> ConcurrentHashMap.newKeySet());
    return Observable.defer(
        () -> {
          TreeSet<LimitOrder> bids = new TreeSet<>();
          TreeSet<LimitOrder> asks = new TreeSet<>();
          AtomicBoolean hasBook = new AtomicBoolean();
          return channel
              .map(
                  arrayNode -> {
                    try {
                      OrderBook book =
                          KrakenStreamingAdapters.adaptOrderbookMessage(
                              depth, bids, asks, currencyPair, arrayNode);
                      hasBook.set(!bids.isEmpty() && !asks.isEmpty());
                      return book;
                    } catch (IllegalStateException e) {
                      LOG.warn(
                          "Resubscribing {} channel after adapter error {}",
                          currencyPair,
                          e.getMessage());
                      bids.clear();
                      asks.clear();
                      hasBook.set(false);
                      requestSnapshot(channelName, args);
                      return new OrderBook(null, new ArrayList<>(), new ArrayList<>(), false);
                    }
                  })
              .filter(ob -> ob.getBids().size() > 0 && ob.getAsks().size() > 0)
              .doOnSubscribe(
                  d -> {
                    // Kraken only sends a snapshot when the channel is (re)subscribed, so a
                    // subscriber joining a live channel would otherwise wait forever for one.
                    boolean channelLive = activeBooks.stream().anyMatch(AtomicBoolean::get);
                    activeBooks.add(hasBook);
                    if (channelLive) {
                      LOG.info("Requesting {} snapshot for a new subscriber", currencyPair);
                      requestSnapshot(channelName, args);
                    }
                  })
              .doFinally(() -> activeBooks.remove(hasBook));
        });
  }

  // Kraken answers a second subscribe on a subscribed channel with "Already subscribed" and no
  // snapshot, so unsubscribe first.
  private void requestSnapshot(String channelName, Object... args) throws IOException {
    service.sendMessage(service.getUnsubscribeMessage(channelName, args));
    service.sendMessage(service.getSubscribeMessage(channelName, args));
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    if (spreadForTicker) {
      String channelName = getChannelName(KrakenSubscriptionName.spread, currencyPair);
      return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
          .map(arrayNode -> KrakenStreamingAdapters.adaptSpreadMessage(currencyPair, arrayNode));
    } else {
      String channelName = getChannelName(KrakenSubscriptionName.ticker, currencyPair);
      return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
          .map(arrayNode -> KrakenStreamingAdapters.adaptTickerMessage(currencyPair, arrayNode));
    }
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    String channelName = getChannelName(KrakenSubscriptionName.trade, currencyPair);
    return subscribe(channelName, MIN_DATA_ARRAY_SIZE, null)
        .flatMap(
            arrayNode ->
                Observable.fromIterable(
                    KrakenStreamingAdapters.adaptTrades(currencyPair, arrayNode)));
  }

  public Observable<KrakenStreamingOhlc> getOHLC(CurrencyPair currencyPair, Integer interval) {
    String channelName = getChannelName(KrakenSubscriptionName.ohlc, currencyPair);
    // args[0] is reserved for an optional order boo depth, we'll use  args[1] for the interval
    Object[] args = new Object[2];
    args[0] = null;
    args[1] = interval;

    return subscribe(channelName, MIN_DATA_ARRAY_SIZE, args)
        .map(arrayNode -> KrakenStreamingAdapters.adaptOhlc(currencyPair, arrayNode));
  }

  public Observable<ArrayNode> subscribe(String channelName, int maxItems, Object... args) {
    return service
        .subscribeChannel(channelName, args)
        .filter(node -> node instanceof ArrayNode)
        .map(node -> (ArrayNode) node)
        .filter(
            list -> {
              if (list.size() < maxItems) {
                LOG.warn(
                    "Invalid message in channel {}. It contains {} array items but expected at least {}",
                    channelName,
                    list.size(),
                    maxItems);
                return false;
              }
              return true;
            });
  }

  public String getChannelName(KrakenSubscriptionName subscriptionName, CurrencyPair currencyPair) {
    String pair = currencyPair.getBase().toString() + "/" + currencyPair.getCounter().toString();
    return subscriptionName + KRAKEN_CHANNEL_DELIMITER + pair;
  }
}
