package info.bitrich.xchangestream.coinbasepro;

import static org.knowm.xchange.coinbasepro.CoinbaseProAdapters.adaptTicker;
import static org.knowm.xchange.coinbasepro.CoinbaseProAdapters.adaptTrades;

import info.bitrich.xchangestream.coinbasepro.dto.CoinbaseProWebSocketTransaction;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.core.Flowable;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProductTicker;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;

/** Created by luca on 4/3/17. */
public class CoinbaseProStreamingMarketDataService implements StreamingMarketDataService {

  private static final String SNAPSHOT = "snapshot";
  private static final String L2UPDATE = "l2update";
  private static final String TICKER = "ticker";
  private static final String MATCH = "match";

  private final CoinbaseProStreamingService service;

  private final Map<CurrencyPair, Flowable<OrderBook>> orderbookSubscriptions = new ConcurrentHashMap<>();

  CoinbaseProStreamingMarketDataService(CoinbaseProStreamingService service) {
    this.service = service;
  }

  private boolean containsPair(List<CurrencyPair> pairs, CurrencyPair pair) {
    for (CurrencyPair item : pairs) {
      if (item.compareTo(pair) == 0) {
        return true;
      }
    }

    return false;
  }

  @Override
  public Flowable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getOrderBook(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for orderbook", currencyPair));

    return orderbookSubscriptions.computeIfAbsent(currencyPair, this::initOrderBookIfAbsent);
  }

  private Flowable<OrderBook> initOrderBookIfAbsent(CurrencyPair currencyPair) {
    OrderbookSubscription subscription =
            new OrderbookSubscription(currencyPair, getRawWebSocketTransactions(currencyPair, false));

    return subscription.stream
            .filter(message -> (SNAPSHOT).equals(message.getType()) || (L2UPDATE).equals(message.getType()))
            .doOnNext(subscription::update)
            .filter(s -> subscription.isOrderbookReady())
            .map(s -> subscription.toOrderBook())
            .publish(1).refCount();
  }

  private final class OrderbookSubscription {
    final CurrencyPair currencyPair;
    final Flowable<CoinbaseProWebSocketTransaction> stream;
    SortedMap<BigDecimal, BigDecimal> bids = new TreeMap<>(java.util.Collections.reverseOrder());
    SortedMap<BigDecimal, BigDecimal> asks = new TreeMap<>();

    Date lastUpdateTime;
    Date lastEmitTime;

    private OrderbookSubscription(CurrencyPair currencyPair, Flowable<CoinbaseProWebSocketTransaction> stream) {
      this.currencyPair = currencyPair;
      this.stream = stream;
    }

    void update(CoinbaseProWebSocketTransaction t) {
      if (t.getType().equals(SNAPSHOT)) {
        initSnapshot(t);
      } else {
        for(String[] change : t.getChanges()) {
          updateMap("buy".equals(change[0]) ? bids : asks, change);
        }
        lastUpdateTime = CoinbaseProStreamingAdapters.parseDate(t.getTime());
      }
    }

    boolean isOrderbookReady() {
      return lastUpdateTime != null && (lastEmitTime == null || lastUpdateTime.getTime() - lastEmitTime.getTime() >= 1000);
    }

    OrderBook toOrderBook() {
      List<LimitOrder> _bids = bids.entrySet().stream().limit(100)
              .map(level -> new LimitOrder(Order.OrderType.BID, level.getValue(), currencyPair, "0", null, level.getKey()))
              .collect(Collectors.toList());

      List<LimitOrder> _asks = asks.entrySet().stream().limit(100)
              .map(level -> new LimitOrder(Order.OrderType.ASK, level.getValue(), currencyPair, "0", null, level.getKey()))
              .collect(Collectors.toList());
      lastEmitTime = lastUpdateTime;
      return new OrderBook(new Date(lastEmitTime.getTime()), _asks, _bids);
    }

    private void initSnapshot(CoinbaseProWebSocketTransaction t) {
      bids = new TreeMap<>(java.util.Collections.reverseOrder());
      asks = new TreeMap<>();
      for (String[] level : t.getBids()) {
        updateMap(bids, level);
      }
      for (String[] level : t.getAsks()) {
        updateMap(asks, level);
      }
      lastUpdateTime = null;
      lastEmitTime = null;
    }

    private void updateMap(SortedMap<BigDecimal, BigDecimal> map, String[] level) {
      BigDecimal price = new BigDecimal(level[level.length - 2]);
      BigDecimal volume = new BigDecimal(level[level.length - 1]);
      if (volume.signum() == 0) {
        map.remove(price);
      } else {
        map.put(price, volume);
      }
    }
  }

  /**
   * Returns an Flowable of {@link CoinbaseProProductTicker}, not converted to {@link Ticker}
   *
   * @param currencyPair the currency pair.
   * @param args optional arguments.
   * @return an Flowable of {@link CoinbaseProProductTicker}.
   */
  public Flowable<CoinbaseProProductTicker> getRawTicker(
      CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getTicker(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for ticker", currencyPair));
    return getRawWebSocketTransactions(currencyPair, true)
        .filter(message -> (TICKER).equals(message.getType()))
        .map(CoinbaseProWebSocketTransaction::toCoinbaseProProductTicker);
  }

  /**
   * Returns the CoinbasePro ticker converted to the normalized XChange object. CoinbasePro does not
   * directly provide ticker data via web service. As stated by:
   * https://docs.coinbasepro.com/#get-product-ticker, we can just listen for 'match' messages.
   *
   * @param currencyPair Currency pair of the ticker
   * @param args optional parameters.
   * @return an Flowable of normalized Ticker objects.
   */
  @Override
  public Flowable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getTicker(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for ticker", currencyPair));
    return getRawWebSocketTransactions(currencyPair, true)
        .filter(message -> (TICKER).equals(message.getType()))
        .map(
            s ->
                adaptTicker(
                    s.toCoinbaseProProductTicker(), s.toCoinbaseProProductStats(), currencyPair));
  }

  @Override
  public Flowable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    if (!containsPair(service.getProduct().getTrades(), currencyPair))
      throw new UnsupportedOperationException(
          String.format("The currency pair %s is not subscribed for trades", currencyPair));
    return getRawWebSocketTransactions(currencyPair, true)
        .filter(message -> (MATCH).equals(message.getType()))
        .filter((CoinbaseProWebSocketTransaction s) -> s.getUserId() == null)
        .map((CoinbaseProWebSocketTransaction s) -> s.toCoinbaseProTrade())
        .map((CoinbaseProTrade t) -> adaptTrades(new CoinbaseProTrade[] {t}, currencyPair))
        .map((Trades h) -> h.getTrades().get(0));
  }

  /**
   * Web socket transactions related to the specified currency, in their raw format.
   *
   * @param currencyPair The currency pair.
   * @return The stream.
   */
  public Flowable<CoinbaseProWebSocketTransaction> getRawWebSocketTransactions(
      CurrencyPair currencyPair, boolean filterChannelName) {
    return service.getRawWebSocketTransactions(currencyPair, filterChannelName);
  }
}
