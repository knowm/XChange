package info.bitrich.xchangestream.core;

import io.reactivex.Observable;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;


public interface StreamingMarketDataService {
    /**
     * Get an order book representing the current offered exchange rates (market depth).
     * Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the WebSocket API.
     *
     * @param currencyPair Currency pair of the order book
     * @return {@link Observable} that emits {@link OrderBook} when exchange sends the update.
     */
    Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args);

    /**
     * Get a ticker representing the current exchange rate.
     * Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the WebSocket API.
     *
     * @param currencyPair Currency pair of the ticker
     * @return {@link Observable} that emits {@link Ticker} when exchange sends the update.
     */
    Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args);

    /**
     * Get the trades performed by the exchange.
     * Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the WebSocket API.
     *
     * @param currencyPair Currency pair of the trades
     * @return {@link Observable} that emits {@link Trade} when exchange sends the update.
     */
    Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args);

    /**
     * Get the changes of order state for the logged-in user.
     *
     * <p>Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When
     * not connected to the WebSocket API <strong>and</strong> authenticated.</p>
     *
     * @param currencyPair Currency pair of the order changes.
     * @return {@link Observable} that emits {@link Order} when exchange sends the update.
     */
    default Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
      throw new NotYetImplementedForExchangeException();
    }

    /**
     * Get the changes of account balance for the logged-in user.
     *
     * <p>Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When
     * not connected to the WebSocket API <strong>and</strong> authenticated.</p>
     *
     * @param currency Currency to monitor.
     * @return {@link Observable} that emits {@link Balance} when exchange sends the update.
     */
    default Observable<Balance> getBalanceChanges(Currency currency, Object... args) {
      throw new NotYetImplementedForExchangeException();
    }

    /**
     * Gets authenticated trades for the logged-in user.
     *
     * <p>Emits {@link info.bitrich.xchangestream.service.exception.NotConnectedException} When
     * not connected to the WebSocket API <strong>and</strong> authenticated.</p>
     *
     * @param currencyPair Currency pair for which to get trades.
     * @return {@link Observable} that emits {@link UserTrade} when exchange sends the update.
     */
    default Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
      throw new NotYetImplementedForExchangeException();
    }
}
