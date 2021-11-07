package info.bitrich.xchangestream.core;

import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;

public interface StreamingMarketDataService {
  /**
   * Get an order book representing the current offered exchange rates (market depth).
   *
   * <p><strong>Warning:</strong> The library will attempt to keep the snapshots returned in sync
   * with the exchange using the approaches published by that exchange. However, there are currently
   * no guarantees that messages will not be skipped, or that any initial state message will be sent
   * on connection. Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException}
   * when not connected to the WebSocket API.
   *
   * @param currencyPair Currency pair of the order book
   * @return {@link Observable} that emits {@link OrderBook} when exchange sends the update.
   */
  default Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("getOrderBook");
  }

  default Observable<OrderBook> getOrderBook(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getOrderBook((CurrencyPair) instrument, args);
    }
    throw new NotYetImplementedForExchangeException("getOrderBook");
  }
  /**
   * Get a ticker representing the current exchange rate. Emits {@link
   * info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the
   * WebSocket API.
   *
   * <p><strong>Warning:</strong> There are currently no guarantees that messages will not be
   * skipped, or that any initial state message will be sent on connection.
   *
   * @param currencyPair Currency pair of the ticker
   * @return {@link Observable} that emits {@link Ticker} when exchange sends the update.
   */
  default Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("getTicker");
  }

  default Observable<Ticker> getTicker(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getTicker((CurrencyPair) instrument, args);
    }
    throw new NotYetImplementedForExchangeException("getTicker");
  }

  /**
   * Get the trades performed by the exchange. Emits {@link
   * info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the
   * WebSocket API.
   *
   * <p><strong>Warning:</strong> There are currently no guarantees that messages will not be
   * skipped.
   *
   * @param currencyPair Currency pair of the trades
   * @return {@link Observable} that emits {@link Trade} when exchange sends the update.
   */
  default Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("getTrades");
  }

  default Observable<Trade> getTrades(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getTrades((CurrencyPair) instrument, args);
    }
    throw new NotYetImplementedForExchangeException("getTrades");
  }
}
