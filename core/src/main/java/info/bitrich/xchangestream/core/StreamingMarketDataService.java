package info.bitrich.xchangestream.core;

import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;


public interface StreamingMarketDataService {
    /**
     * Get an order book representing the current offered exchange rates (market depth).
     *
     * @param currencyPair Currency pair of the order book
     * @return {@link Observable} that emits {@link OrderBook} when exchange sends the update.
     * Emits {@link IllegalStateException} When not connected to the WebSocket API.
     */
    Observable<OrderBook> getOrderBook(CurrencyPair currencyPair, Object... args);

    /**
     * Get a ticker representing the current exchange rate.
     *
     * @param currencyPair Currency pair of the ticker
     * @return {@link Observable} that emits {@link OrderBook} when exchange sends the update.
     * Emits {@link IllegalStateException} When not connected to the WebSocket API.
     */
    Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args);

    /**
     * Get the trades performed by the exchange.
     *
     * @param currencyPair Currency pair of the trades
     * @return {@link Observable} that emits {@link OrderBook} when exchange sends the update.
     * Emits {@link IllegalStateException} When not connected to the WebSocket API.
     */
    Observable<Trade> getTrades(CurrencyPair currencyPair, Object... args);
}
