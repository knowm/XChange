package info.bitrich.xchangestream.cryptocom;

import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.cryptocom.dto.CryptoComUserTradeUpdate;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.dto.trade.CryptoComOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.instrument.Instrument;

public class CryptoComStreamingTradeService implements StreamingTradeService {

  private final CryptoComPrivateStreamingService service;

  public CryptoComStreamingTradeService(CryptoComPrivateStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getOrderChanges((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    String channel = "user.order." + CryptoComAdapters.toInstrumentName(instrument);
    return service
        .subscribeChannel(channel)
        .flatMapIterable(message -> service.extractData(message, CryptoComOrder.class))
        .map(CryptoComAdapters::adaptOrder);
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getUserTrades((Instrument) currencyPair, args);
  }

  @Override
  public Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    String channel = "user.trade." + CryptoComAdapters.toInstrumentName(instrument);
    return service
        .subscribeChannel(channel)
        .flatMapIterable(message -> service.extractData(message, CryptoComUserTradeUpdate.class))
        .map(CryptoComStreamingAdapters::adaptUserTrade);
  }
}
