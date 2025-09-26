package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import info.bitrich.xchangestream.kraken.dto.common.ChannelType;
import info.bitrich.xchangestream.kraken.dto.response.KrakenDataMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenTickerMessage;
import info.bitrich.xchangestream.kraken.dto.response.KrakenTradeMessage;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.instrument.Instrument;

public class KrakenStreamingMarketDataService implements StreamingMarketDataService {

  private final KrakenStreamingService service;

  public KrakenStreamingMarketDataService(KrakenStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    return service
        .subscribeChannel(ChannelType.TICKER.getValue(), instrument)
        .map(KrakenTickerMessage.class::cast)
        .map(KrakenDataMessage::getPayload)
        .map(KrakenStreamingAdapters::toTicker);
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    return service
        .subscribeChannel(ChannelType.TRADE.getValue(), instrument)
        .map(KrakenTradeMessage.class::cast)
        .map(KrakenDataMessage::getPayload)
        .map(KrakenStreamingAdapters::toTrade);
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return getTrades((Instrument) currencyPair, args);
  }
}
