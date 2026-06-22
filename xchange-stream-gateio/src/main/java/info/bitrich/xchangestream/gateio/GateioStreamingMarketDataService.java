package info.bitrich.xchangestream.gateio;

import com.google.common.collect.Lists;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.funding.GateioTickerAndFundingNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookV2FuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookV2Notification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioFuturesTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.jspecify.annotations.NonNull;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.*;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.instrument.Instrument;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class GateioStreamingMarketDataService implements StreamingMarketDataService {

  public static final int MAX_DEPTH_DEFAULT = 5;
  public static final Duration UPDATE_INTERVAL_DEFAULT = Duration.ofMillis(100);
  private final GateioStreamingService service;
  private final Map<String, OrderBook> orderBookMap = new HashMap<>();
  private final ExchangeMetaData exchangeMetaData;
  private Disposable fundingRateInfoUpdate;
  private GateioMarketDataService gateioMarketDataService;


  public GateioStreamingMarketDataService(GateioStreamingService service, ExchangeMetaData exchangeMetaData, GateioMarketDataService marketDataService) {
    this.service = service;
    this.exchangeMetaData = exchangeMetaData;
    this.gateioMarketDataService = marketDataService;
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    Integer orderBookLevel = (Integer) ArrayUtils.get(args, 0, MAX_DEPTH_DEFAULT);
    String channelName =
        (instrument instanceof FuturesContract)
            ? Config.FUTURES_ORDERBOOKV2_CHANNEL
            : Config.SPOT_ORDERBOOKV2_CHANNEL;
    Observable<GateioWsNotification> updates =
        service.subscribeChannel(channelName, instrument, orderBookLevel);
    AtomicLong orderBookUpdateIdPrev = new AtomicLong();
    BigDecimal contractValue;
    if (instrument instanceof FuturesContract) {
      contractValue = exchangeMetaData.getInstruments()
          .get(instrument).getContractValue();
      return getOrderBookObservableFutures(instrument, updates, contractValue, orderBookUpdateIdPrev, channelName, orderBookLevel);
    }
    else {
      return getOrderBookObservable(instrument, updates, orderBookUpdateIdPrev, channelName, orderBookLevel);
    }
  }

  private @NonNull Observable<OrderBook> getOrderBookObservable(Instrument instrument, Observable<GateioWsNotification> updates, AtomicLong orderBookUpdateIdPrev, String channelName, Integer orderBookLevel) {
    return updates
        .map(GateioOrderBookV2Notification.class::cast)
        .flatMap(ob -> {
          OrderBook orderBook;
          if (ob.getResult().isFull()) {
            orderBook = GateioStreamingAdapters.toOrderBookV2(ob);
            orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
            orderBookMap.put(instrument.toString(), orderBook);
          } else {
            debugLog(orderBookUpdateIdPrev, ob.getResult().getFirstUpdateId(), ob.getResult().getLastUpdateId());
            if (orderBookUpdateIdPrev.incrementAndGet() == ob.getResult().getFirstUpdateId()) {
              orderBook = orderBookMap.getOrDefault(instrument.toString(), null);
              if (orderBook == null) {
                log.error("Failed to get orderBook, instId={}.", instrument);
                return Observable.fromIterable(new LinkedList<>());
              }
              List<OrderBookUpdate> orderBookUpdates;
              orderBookUpdates =
                  GateioStreamingAdapters.adaptOrderBookUpdates(
                      instrument,
                      ob.getResult());
              orderBookUpdates.forEach(orderBook::update);
              orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
              return Observable.just(orderBook);
            } else {
              errorHandler(orderBookUpdateIdPrev, ob.getResult().getFirstUpdateId(), instrument, channelName, orderBookLevel);
            }
          }
          return Observable.just(new OrderBook(null, Lists.newArrayList(), Lists.newArrayList(), false));
        });
  }

  private @NonNull Observable<OrderBook> getOrderBookObservableFutures(Instrument instrument, Observable<GateioWsNotification> updates, BigDecimal contractValue, AtomicLong orderBookUpdateIdPrev, String channelName, Integer orderBookLevel) {
    return updates
        .map(GateioOrderBookV2FuturesNotification.class::cast)
        .flatMap(ob -> {
          OrderBook orderBook;
          if (ob.getResult().isFull()) {
            orderBook = GateioStreamingAdapters.toOrderBookV2Futures(ob, contractValue);
            orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
            orderBookMap.put(instrument.toString(), orderBook);
          } else {
            debugLog(orderBookUpdateIdPrev, ob.getResult().getFirstUpdateId(), ob.getResult().getLastUpdateId());
            if (orderBookUpdateIdPrev.incrementAndGet() == ob.getResult().getFirstUpdateId()) {
              orderBook = orderBookMap.getOrDefault(instrument.toString(), null);
              if (orderBook == null) {
                log.error("Failed to get orderBook, instId={}.", instrument);
                return Observable.fromIterable(new LinkedList<>());
              }
              List<OrderBookUpdate> orderBookUpdates;
              orderBookUpdates = GateioStreamingAdapters.adaptOrderBookFuturesUpdates(
                  instrument,
                  ob.getResult(),
                  contractValue);
              orderBookUpdates.forEach(orderBook::update);
              orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
              return Observable.just(orderBook);
            } else {
              errorHandler(orderBookUpdateIdPrev, ob.getResult().getFirstUpdateId(), instrument, channelName, orderBookLevel);
            }
          }
          return Observable.just(new OrderBook(null, Lists.newArrayList(), Lists.newArrayList(), false));
        });
  }

  private static void debugLog(AtomicLong orderBookUpdateIdPrev, long firstUpdateId, long lastUpdateId) {
    log.debug(
        "orderBookUpdate U {}, u {}, orderBookUpdateIdPrev {} ",
        firstUpdateId,
        lastUpdateId, orderBookUpdateIdPrev.get());
  }

  private void errorHandler(AtomicLong orderBookUpdateIdPrev, Long ob, Instrument instrument, String channelName, Integer orderBookLevel) throws IOException {
    log.error(
        "orderBookUpdate id sequence failed, expected {}, in fact {}",
        orderBookUpdateIdPrev.get(),
        ob);
    log.warn(
        "Resubscribing {} channel after error",
        instrument);
    // Resubscribe to the channel, triggering a new snapshot
    if (orderBookMap.containsKey(instrument.toString())) {
      orderBookMap.remove(instrument.toString());
      if (service.isSocketOpen()) {
        service.sendMessage(service.getUnsubscribeMessage(channelName, instrument, orderBookLevel));
        service.resubscribeChannels();
      }
    }
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
   * @param args         Order book level: {@link Integer}, update speed: {@link Duration}
   */
  public Observable<OrderBook> getOrderBookLegacy(CurrencyPair currencyPair, Object... args) {
    Integer orderBookLevel = (Integer) ArrayUtils.get(args, 0, MAX_DEPTH_DEFAULT);
    Duration updateSpeed = (Duration) ArrayUtils.get(args, 1, UPDATE_INTERVAL_DEFAULT);
    return service
        .subscribeChannel(
            Config.SPOT_ORDERBOOK_CHANNEL, new Object[]{currencyPair, orderBookLevel, updateSpeed})
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

  @Override
  public Observable<FundingRate> getFundingRate(Instrument instrument, Object... args) {
    try {
      // init update info for funding rate interval
      synchronized (this) {
        if (fundingRateInfoUpdate == null) {
          fundingRateInfoUpdate =
              Observable.interval(10, 10, TimeUnit.MINUTES).subscribe(x -> updateFundingRateInfo());
        }
      }
    } catch (Exception e) {
      return Observable.error(e);
    }
    return service
        .subscribeChannel(Config.FUTURES_TICKET_AND_FUNDING_CHANNEL, instrument)
        .map(GateioTickerAndFundingNotification.class::cast)
        .map(data -> GateioStreamingAdapters.toFunding(data.getResult()));

  }

  private void updateFundingRateInfo() {
    try {
      gateioMarketDataService.u
          fundingRateInfoMap =
          marketDataService.getBinanceFundingRateInfo().stream()
              .collect(
                  Collectors.toMap(
                      BinanceFundingRateInfo::getInstrument,
                      BinanceFundingRateInfo::getFundingIntervalHours));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
