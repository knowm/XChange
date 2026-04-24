package info.bitrich.xchangestream.gateio;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderFuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.order.GateioSingleOrderNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioSingleUserTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.gateio.GateioAdapters;
import org.knowm.xchange.instrument.Instrument;

public class GateioStreamingTradeService implements StreamingTradeService {

  private final GateioStreamingService service;

  public GateioStreamingTradeService(GateioStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_TRADES_CHANNEL, currencyPair)
        .map(GateioSingleUserTradeNotification.class::cast)
        .map(GateioStreamingAdapters::toUserTrade);
  }

  @Override
  public Observable<UserTrade> getUserTrades() {
    return getUserTrades(null);
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getOrderChanges((CurrencyPair) instrument, args);
    }
    if (instrument instanceof FuturesContract) {
      return service
          .subscribeChannel(Config.FUTURES_USER_ORDERS_CHANNEL, ((FuturesContract) instrument).getCurrencyPair())
          .map(GateioSingleOrderFuturesNotification.class::cast)
          .flatMapIterable(GateioSingleOrderFuturesNotification::getResult)
          .map(GateioAdapters::toOrder);
    }
    throw new IllegalArgumentException("Instrument type not supported: " + instrument.getClass());
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_ORDERS_CHANNEL, currencyPair)
        .map(GateioSingleOrderNotification.class::cast)
        .map(GateioStreamingAdapters::toOrder);
  }
}
