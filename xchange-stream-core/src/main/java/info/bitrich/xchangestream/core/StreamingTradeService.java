package info.bitrich.xchangestream.core;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public interface StreamingTradeService {

  /**
   * Get the changes of order state for the logged-in user.
   *
   * <p><strong>Warning:</strong> there are currently no guarantees that messages will arrive in
   * order, that messages will not be skipped, or that any initial state message will be sent on
   * connection. Most exchanges have a recommended approach for managing this, involving timestamps,
   * sequence numbers and a separate REST API for re-sync when inconsistencies appear. You should
   * implement these approaches, if required, by combining calls to this method with {@link
   * TradeService#getOpenOrders()}.
   *
   * <p><strong>Emits</strong> {@link
   * info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the
   * WebSocket API.
   *
   * <p><strong>Immediately throws</strong> {@link ExchangeSecurityException} if called without
   * authentication details
   *
   * @param currencyPair Currency pair of the order changes.
   * @return {@link Observable} that emits {@link Order} when exchange sends the update.
   */
  default Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("getOrderChanges");
  }

  default Observable<Order> getOrderChanges(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getOrderChanges((CurrencyPair) instrument, args);
    }
    throw new NotYetImplementedForExchangeException("getOrderChanges");
  }

  /**
   * Gets authenticated trades for the logged-in user.
   *
   * <p><strong>Warning:</strong> there are currently no guarantees that messages will arrive in
   * order, that messages will not be skipped, or that any initial state message will be sent on
   * connection. Most exchanges have a recommended approach for managing this, involving timestamps,
   * sequence numbers and a separate REST API for re-sync when inconsistencies appear. You should
   * implement these approaches, if required, by combining calls to this method with {@link
   * TradeService#getTradeHistory(org.knowm.xchange.service.trade.params.TradeHistoryParams)}
   *
   * <p><strong>Emits</strong> {@link
   * info.bitrich.xchangestream.service.exception.NotConnectedException} When not connected to the
   * WebSocket API.
   *
   * <p><strong>Immediately throws</strong> {@link ExchangeSecurityException} if called without
   * authentication details
   *
   * @param currencyPair Currency pair for which to get trades.
   * @return {@link Observable} that emits {@link UserTrade} when exchange sends the update.
   */
  default Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    throw new NotYetImplementedForExchangeException("getUserTrades");
  }

  default Observable<UserTrade> getUserTrades(Instrument instrument, Object... args) {
    if (instrument instanceof CurrencyPair) {
      return getUserTrades((CurrencyPair) instrument, args);
    }
    throw new NotYetImplementedForExchangeException("getUserTrades");
  }

  default Observable<UserTrade> getUserTrades() {
    throw new NotYetImplementedForExchangeException("getAllUserTrades");
  }

  default Observable<OpenPosition> getPositionChanges(Instrument instrument) {
    throw new NotYetImplementedForExchangeException("getPositionChanges");
  }

  /**
   * Places a market order on the exchange. A market order is an order to buy or sell a financial instrument immediately at the best available current price. Specific exchanges may require additional
   * optional parameters to process the market order.
   *
   * @param marketOrder The details of the market order, including the instrument, amount, and side (buy/sell).
   * @param args        Optional additional arguments that may be required by specific exchanges.
   * @return A {@link Single} that emits the operation result code, typically 0 for success.
   */
  default Single<Integer> placeMarketOrder(MarketOrder marketOrder, Object... args) {
    throw new NotYetImplementedForExchangeException("placeMarketOrder");
  }

  /**
   * Places a limit order on the exchange. A limit order specifies the price at which the user wants to buy or sell a financial instrument, potentially with additional optional parameters.
   *
   * @param limitOrder The details of the limit order, including instrument, price, volume, and side (buy/sell).
   * @param args       Optional additional arguments that may be required by specific exchanges.
   * @return A {@link Single} that emits the operation result code, typically 0 for success.
   */
  default Single<Integer> placeLimitOrder(LimitOrder limitOrder, Object... args) {
    throw new NotYetImplementedForExchangeException("placeLimitOrder");
  }

  /**
   * Modifies an existing limit order on the exchange. This can include changes to the price, volume, or other adjustable parameters of the order, depending on the exchange's specific functionality
   * and constraints.
   *
   * @param order The limit order to be modified, including the updated details such as price or/and volume.
   * @param args  Optional additional arguments that may be required by specific exchanges.
   * @return A {@link Single} that emits the operation result code, typically 0 for success.
   */
  default Single<Integer> changeOrder(LimitOrder order, Object... args) {
    throw new NotYetImplementedForExchangeException("changeOrder");
  }

  /**
   * Cancels an existing order on the exchange. The operation requires specific parameters describing the order to be canceled, such as order ID or user reference ID.
   *
   * @param params An object implementing {@link CancelOrderParams} that contains order ID or user reference ID.
   * @param args   Optional additional arguments that may be required by specific exchanges.
   * @return A {@link Single} that emits the operation result code, typically 0 for success.
   */
  default Single<Integer> cancelOrder(CancelOrderParams params, Object... args) {
    throw new NotYetImplementedForExchangeException("cancelOrder");
  }
}
