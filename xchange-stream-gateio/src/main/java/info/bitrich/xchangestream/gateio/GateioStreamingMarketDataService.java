package info.bitrich.xchangestream.gateio;

import com.google.common.collect.Lists;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.GateioWsNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookV2Notification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioFuturesTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.OrderBookUpdate;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.instrument.Instrument;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class GateioStreamingMarketDataService implements StreamingMarketDataService {

  public static final int MAX_DEPTH_DEFAULT = 5;
  public static final Duration UPDATE_INTERVAL_DEFAULT = Duration.ofMillis(100);
  private final GateioStreamingService service;
  private final Map<String, OrderBook> orderBookMap = new HashMap<>();
  private final ExchangeMetaData exchangeMetaData;

  public GateioStreamingMarketDataService(GateioStreamingService service, ExchangeMetaData exchangeMetaData) {
    this.service = service;
    this.exchangeMetaData = exchangeMetaData;
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
    if (instrument instanceof FuturesContract)
      contractValue = exchangeMetaData.getInstruments()
          .get(instrument).getContractValue();
    else {
      contractValue = BigDecimal.ONE;
    }
    return updates
        .map(GateioOrderBookV2Notification.class::cast)
        .flatMap(ob -> {
          OrderBook orderBook;
          if (ob.getResult().isFull()) {
            if (instrument instanceof FuturesContract)
              orderBook = GateioStreamingAdapters.toOrderBookV2Futures(ob, contractValue);
            else
              orderBook = GateioStreamingAdapters.toOrderBookV2(ob);
            orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
            orderBookMap.put(instrument.toString(), orderBook);
          } else {
            log.debug(
                "orderBookUpdate U {}, u {}, orderBookUpdateIdPrev {} ",
                ob.getResult().getFirstUpdateId(),
                ob.getResult().getLastUpdateId(), orderBookUpdateIdPrev.get());
            if (orderBookUpdateIdPrev.incrementAndGet() == ob.getResult().getFirstUpdateId()) {
              orderBook = orderBookMap.getOrDefault(instrument.toString(), null);
              if (orderBook == null) {
                log.error("Failed to get orderBook, instId={}.", instrument);
                return Observable.fromIterable(new LinkedList<>());
              }
              List<OrderBookUpdate> orderBookUpdates;
              if (instrument instanceof FuturesContract)
                orderBookUpdates = GateioStreamingAdapters.adaptOrderBookFuturesUpdates(
                    instrument,
                    ob.getResult(),
                    contractValue);
              else
                orderBookUpdates =
                    GateioStreamingAdapters.adaptOrderBookUpdates(
                        instrument,
                        ob.getResult());
              orderBookUpdates.forEach(orderBook::update);
              orderBookUpdateIdPrev.set(ob.getResult().getLastUpdateId());
              return Observable.just(orderBook);
            } else {
              log.error(
                  "orderBookUpdate id sequence failed, expected {}, in fact {}",
                  orderBookUpdateIdPrev.get(),
                  ob.getResult().getFirstUpdateId());
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
          }
          return Observable.just(new OrderBook(null, Lists.newArrayList(), Lists.newArrayList(), false));
        });
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

}
