package info.bitrich.xchangestream.deribit;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTickerNotification;
import info.bitrich.xchangestream.deribit.dto.response.DeribitTradeNotification;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitAdapters;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;

public class DeribitStreamingMarketDataService implements StreamingMarketDataService {

  private final DeribitStreamingService service;

  public DeribitStreamingMarketDataService(DeribitStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    var channelName = String.format("ticker.%s.100ms", DeribitAdapters.toString(instrument));
    return service
        .subscribeChannel(channelName)
        .map(DeribitTickerNotification.class::cast)
        .map(DeribitStreamingAdapters::toTicker);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return getTrades((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    var channelName = String.format("trades.%s.100ms", DeribitAdapters.toString(instrument));
    return service
        .subscribeChannel(channelName)
        .map(DeribitTradeNotification.class::cast)
        .map(DeribitStreamingAdapters::toTrade);
  }
}
