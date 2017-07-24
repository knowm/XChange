package org.knowm.xchange.service.trade;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

/**
 * <p>
 * Interface to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>Retrieve the user's open orders on the exchange</li>
 * <li>Cancel user's open orders on the exchange</li>
 * <li>Place market orders on the exchange</li>
 * <li>Place limit orders on the exchange</li>
 * </ul>
 * <p>
 * The implementation of this service is expected to be based on a client polling mechanism of some kind
 * </p>
 */
public interface TradeService extends BaseService {

  /**
   * Gets the open orders
   *
   * @return the open orders, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   * @deprecated Use {@link #getOpenOrders(OpenOrdersParams)} instead. Will be removed in the future release.
   */
  @Deprecated
  OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Gets the open orders
   *
   * @param params The parameters describing the filter. Note that {@link OpenOrdersParams} is an empty interface. Exchanges should implement its own
   * params object. Params should be create with {@link #createOpenOrdersParams()}.
   * @return the open orders, null if some sort of error occurred. Implementers should log the error.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  OpenOrders getOpenOrders(
      OpenOrdersParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Place a market order
   *
   * @param marketOrder
   * @return the order ID
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  String placeMarketOrder(
      MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Place a limit order
   *
   * @param limitOrder
   * @return the order ID
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  String placeLimitOrder(
      LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * cancels order with matching orderId (conveniance method, typical just delegate to cancelOrder(CancelOrderByIdParams))
   *
   * @param orderId
   * @return true if order was successfully cancelled, false otherwise.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * cancels order with matching orderParams
   *
   * @param orderParams
   * @return true if order was successfully cancelled, false otherwise.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  boolean cancelOrder(CancelOrderParams orderParams) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

  /**
   * Fetch the history of user trades.
   * <p/>
   * If you are calling this method for single exchange, known at the development time, you may pass an object of specific *TradeHistoryParam class
   * that is nested it that exchange's trade service.
   * <p/>
   * If, however, you are fetching user trade history from many exchanges using the same code, you will find useful to create the parameter object
   * with {@link #createTradeHistoryParams()} and check which parameters are required or supported using instanceof operator. See subinterfaces of
   * {@link TradeHistoryParams}. Note that whether an interface is required or supported will vary from exchange to exchange and it's described only
   * through the javadoc.
   * <p/>
   * There is also implementation of all the common interfaces, {@link TradeHistoryParamsAll} , that, with all properties set non-null, should work
   * with any exchange.
   * <p/>
   * Some exchanges allow extra parameters, not covered by any common interface. To access them, you will have to use the object returned by
   * {@link #createTradeHistoryParams()} and cast it to the exchange-specific type.
   *
   * @param params The parameters describing the filter. Note that {@link TradeHistoryParams} is an empty interface. Exact set of interfaces that are
   * required or supported by this method is described by the type of object returned from {@link #createTradeHistoryParams()} and the javadoc of the
   * method.
   * @return UserTrades as returned by the exchange API
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   * @see #createTradeHistoryParams()
   * @see TradeHistoryParamsAll
   */
  UserTrades getTradeHistory(TradeHistoryParams params) throws IOException;

  /**
   * Create {@link TradeHistoryParams} object specific to this exchange. Object created by this method may be used to discover supported and required
   * {@link #getTradeHistory(TradeHistoryParams)} parameters and should be passed only to the method in the same class as the createTradeHistoryParams
   * that created the object.
   */
  TradeHistoryParams createTradeHistoryParams();

  /**
   * Create {@link OpenOrdersParams} object specific to this exchange. Object created by this method may be used to discover supported and required
   * {@link #getOpenOrders(OpenOrdersParams)} parameters and should be passed only to the method in the same class as the createOpenOrdersParams that
   * created the object.
   */
  OpenOrdersParams createOpenOrdersParams();

  /**
   * Verify the order against the exchange meta data. Most implementations will require that {@link org.knowm.xchange.Exchange#remoteInit()} be called
   * before this method
   */
  void verifyOrder(LimitOrder limitOrder);

  /**
   * Verify the order against the exchange meta data. Most implementations will require that {@link org.knowm.xchange.Exchange#remoteInit()} be called
   * before this method
   */
  void verifyOrder(MarketOrder marketOrder);

  /**
   * get's the latest order form the order book that with matching orderId
   *
   * @return the order as it is on the exchange.
   * @throws ExchangeException                     - Indication that the exchange reported some kind of error with the request or response
   * @throws NotAvailableFromExchangeException     - Indication that the exchange does not support the requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the requested function or data, but it has not yet been
   *                                               implemented
   * @throws IOException                           - Indication that a networking error occurred while fetching JSON data
   */
  Collection<Order> getOrder(
      String... orderIds) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException;

}
