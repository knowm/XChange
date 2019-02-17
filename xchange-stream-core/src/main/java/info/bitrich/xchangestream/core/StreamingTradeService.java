package info.bitrich.xchangestream.core;

import io.reactivex.Observable;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public interface StreamingTradeService {

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