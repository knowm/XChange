package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioFuturesTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import org.apache.commons.lang3.ArrayUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;

import java.time.Duration;

public class GateioStreamingMarketDataService implements StreamingMarketDataService {

  public static final int MAX_DEPTH_DEFAULT = 5;
  public static final Duration UPDATE_INTERVAL_DEFAULT = Duration.ofMillis(100);
  private final GateioStreamingService service;

  public GateioStreamingMarketDataService(GateioStreamingService service) {
    this.service = service;
  }

  /**
   * Uses the limited-level snapshot method:
   * https://www.gate.io/docs/apiv4/ws/index.html#limited-level-full-order-book-snapshot
   *
   * @param currencyPair Currency pair of the order book
   * @param args Order book level: {@link Integer}, update speed: {@link Duration}
   */
  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
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
}
