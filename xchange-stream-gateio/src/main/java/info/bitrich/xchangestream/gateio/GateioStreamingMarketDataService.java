package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookFuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.OrderBookV2FuturesResponse;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioFuturesTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.*;

public class GateioStreamingMarketDataService implements StreamingMarketDataService {

  public static final int MAX_DEPTH_DEFAULT = 5;
  public static final Duration UPDATE_INTERVAL_DEFAULT = Duration.ofMillis(100);
  private final GateioStreamingService service;

  public GateioStreamingMarketDataService(GateioStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    Integer orderBookLevel = (Integer) ArrayUtils.get(args, 0, MAX_DEPTH_DEFAULT);

    String channelName =
        (instrument instanceof FuturesContract)
            ? Config.FUTURES_ORDERBOOK_CHANNEL
            : Config.SPOT_ORDERBOOK_CHANNEL;

//    String currencyPair = instrument.getCounter()+"/"+instrument.getBase();
    Observable<GateioWsNotification> updates =
        service.subscribeChannel(channelName, instrument, orderBookLevel);

//    if (instrument instanceof FuturesContract) {
//      return updates
//          .map(GateioOrderBookFuturesNotification.class::cast)
//          .scan(new FuturesOrderBookState((FuturesContract) instrument), FuturesOrderBookState::apply)
//          .skip(1)
//          .map(FuturesOrderBookState::toOrderBook);

    return updates
        .map(GateioOrderBookFuturesNotification.class::cast)
        .map(GateioStreamingAdapters::toOrderBookFutures);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook((Instrument) currencyPair, args);
  }

  /**
   * Uses the limited-level snapshot method:
   * https://www.gate.io/docs/apiv4/ws/index.html#limited-level-full-order-book-snapshot
   *
   * @param currencyPair Currency pair of the order book
   * @param args Order book level: {@link Integer}, update speed: {@link Duration}
   */
  public Observable<OrderBook> getOrderBookLegacy(CurrencyPair currencyPair, Object... args) {
    Integer orderBookLevel = (Integer) ArrayUtils.get(args, 0, MAX_DEPTH_DEFAULT);
    Duration updateSpeed = (Duration) ArrayUtils.get(args, 1, UPDATE_INTERVAL_DEFAULT);
    return service
        .subscribeChannel(
            Config.SPOT_ORDERBOOK_CHANNEL, new Object[] {currencyPair, orderBookLevel, updateSpeed})
        .map(GateioOrderBookNotification.class::cast)
        .map(GateioStreamingAdapters::toOrderBook);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_TICKERS_CHANNEL, currencyPair)
        .map(GateioTickerNotification.class::cast)
        .map(GateioStreamingAdapters::toTicker);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    if (instrument instanceof FuturesContract) {
      return service
          .subscribeChannel(
              Config.FUTURES_TRADES_CHANNEL, ((FuturesContract) instrument).getCurrencyPair())
          .map(GateioFuturesTradeNotification.class::cast)
          .flatMapIterable(GateioFuturesTradeNotification::getResult)
          .map(payload -> {
            Trade trade = GateioStreamingAdapters.toTradeFutures(payload);
            return Trade.builder()
                .type(trade.getType())
                .originalAmount(trade.getOriginalAmount())
                .instrument(instrument)
                .price(trade.getPrice())
                .timestamp(trade.getTimestamp())
                .id(trade.getId())
                .build();
          });
    }
    if (instrument instanceof CurrencyPair) {
      return getTrades((CurrencyPair) instrument, args);
    }
    throw new IllegalArgumentException("Instrument type not supported: " + instrument.getClass());
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_TRADES_CHANNEL, currencyPair)
        .map(GateioTradeNotification.class::cast)
        .map(GateioStreamingAdapters::toTrade);
  }

  private static final class FuturesOrderBookState {

    private final FuturesContract instrument;
    private final TreeMap<BigDecimal, BigDecimal> asks;
    private final TreeMap<BigDecimal, BigDecimal> bids;
    private Date timestamp;

    private FuturesOrderBookState(FuturesContract instrument) {
      this(instrument, new TreeMap<>(), new TreeMap<>(Comparator.reverseOrder()), null);
    }

    private FuturesOrderBookState(
        FuturesContract instrument,
        TreeMap<BigDecimal, BigDecimal> asks,
        TreeMap<BigDecimal, BigDecimal> bids,
        Date timestamp) {
      this.instrument = instrument;
      this.asks = asks;
      this.bids = bids;
      this.timestamp = timestamp;
    }

    private FuturesOrderBookState apply(GateioOrderBookFuturesNotification notification) {
      OrderBookV2FuturesResponse payload = notification.getResult();

      if (payload.isFull()) {
        asks.clear();
        bids.clear();
      }

      applySide(asks, payload.getAsks());
      applySide(bids, payload.getBids());
      timestamp = Date.from(payload.getTimestamp());
      return this;
    }

    private void applySide(
        Map<BigDecimal, BigDecimal> book, List<OrderBookV2FuturesResponse.PriceSizeEntry> entries) {
      if (entries == null) {
        return;
      }

      for (OrderBookV2FuturesResponse.PriceSizeEntry entry : entries) {
        if (entry.getSize() == null || entry.getPrice() == null) {
          continue;
        }
        if (entry.getSize().compareTo(BigDecimal.ZERO) == 0) {
          book.remove(entry.getPrice());
        } else {
          book.put(entry.getPrice(), entry.getSize());
        }
      }
    }

    private OrderBook toOrderBook() {
      return new OrderBook(timestamp, toOrders(asks, OrderType.ASK), toOrders(bids, OrderType.BID));
    }

    private List<LimitOrder> toOrders(Map<BigDecimal, BigDecimal> levels, OrderType type) {
      List<LimitOrder> orders = new ArrayList<>(levels.size());
      for (Map.Entry<BigDecimal, BigDecimal> entry : levels.entrySet()) {
        orders.add(
            new LimitOrder(type, entry.getValue(), instrument, null, null, entry.getKey()));
      }
      return orders;
    }
  }
}
