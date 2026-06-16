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
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class GateioStreamingTradeService implements StreamingTradeService {

  private final GateioStreamingService service;
  private final ExchangeMetaData exchangeMetaData;

  public GateioStreamingTradeService(GateioStreamingService service, ExchangeMetaData exchangeMetaData) {
    this.service = service;
    this.exchangeMetaData = exchangeMetaData;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_TRADES_CHANNEL, currencyPair)
//        .filter(GateioSingleUserTradeNotification.class::isInstance)
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
          .subscribeChannel(Config.FUTURES_USER_ORDERS_CHANNEL, instrument)
//          .filter(GateioSingleOrderFuturesNotification.class::isInstance)
          .map(GateioSingleOrderFuturesNotification.class::cast)
          .map(m -> GateioStreamingAdapters.toOrder
              (m, exchangeMetaData.getInstruments().get(instrument).getContractValue()));
    }
    throw new IllegalArgumentException("Instrument type not supported: " + instrument.getClass());
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(Config.SPOT_USER_ORDERS_CHANNEL, currencyPair)
//        .filter(GateioSingleOrderNotification.class::isInstance)
        .map(GateioSingleOrderNotification.class::cast)
        .map(GateioStreamingAdapters::toOrder);
  }
}
