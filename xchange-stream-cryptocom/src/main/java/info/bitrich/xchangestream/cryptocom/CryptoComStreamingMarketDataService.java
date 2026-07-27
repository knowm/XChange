package info.bitrich.xchangestream.cryptocom;

import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.cryptocom.CryptoComAdapters;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComOrderBookData;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComPublicTrade;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;

public class CryptoComStreamingMarketDataService implements StreamingMarketDataService {

  private static final int DEFAULT_BOOK_DEPTH = 10;

  private final CryptoComStreamingService service;

  public CryptoComStreamingMarketDataService(CryptoComStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return getTicker((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    String channel = "ticker." + CryptoComAdapters.toInstrumentName(instrument);
    return service
        .subscribeChannel(channel)
        .flatMapIterable(message -> service.extractData(message, CryptoComTicker.class))
        .map(CryptoComAdapters::adaptTicker);
  }

  @Override
  public Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    return getOrderBook((Instrument) currencyPair, args);
  }

  @Override
  public Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    if (!(instrument instanceof CurrencyPair)) {
      throw new NotYetImplementedForExchangeException("getOrderBook");
    }
    CurrencyPair pair = (CurrencyPair) instrument;
    int depth =
        args != null && args.length > 0 && args[0] instanceof Integer
            ? (Integer) args[0]
            : DEFAULT_BOOK_DEPTH;
    String channel = "book." + CryptoComAdapters.toInstrumentName(instrument) + "." + depth;
    return service
        .subscribeChannel(channel)
        .flatMapIterable(message -> service.extractData(message, CryptoComOrderBookData.class))
        .map(data -> CryptoComAdapters.adaptOrderBook(data, pair));
  }

  @Override
  public Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    return getTrades((Instrument) currencyPair, args);
  }

  @Override
  public Observable<Trade> getTrades(Instrument instrument, Object... args) {
    if (!(instrument instanceof CurrencyPair)) {
      throw new NotYetImplementedForExchangeException("getTrades");
    }
    CurrencyPair pair = (CurrencyPair) instrument;
    String channel = "trade." + CryptoComAdapters.toInstrumentName(instrument);
    return service
        .subscribeChannel(channel)
        .flatMapIterable(message -> service.extractData(message, CryptoComPublicTrade.class))
        .map(data -> CryptoComAdapters.adaptTrade(data, pair));
  }
}
