package org.knowm.xchange.service.trade;

import java.io.IOException;
import java.util.Collection;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.OpenPositions;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParam;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.knowm.xchange.service.trade.params.orders.OrderQueryParams;

/**
 * Interface to provide the following to {@link org.knowm.xchange.Exchange}:
 *
 * <ul>
 *   <li>Retrieve the user's open orders on the exchange
 *   <li>Cancel user's open orders on the exchange
 *   <li>Place market orders on the exchange
 *   <li>Place limit orders on the exchange
 *   <li>Change limit orders on the exchange
 * </ul>
 *
 * <p>The implementation of this service is expected to be based on a client polling mechanism of
 * some kind
 */
public interface TradeService extends BaseService {

  /**
   * Gets the open orders
   *
   * @return the open orders, null if some sort of error occurred. Implementers should log the
   *     error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default OpenOrders getOpenOrders() throws IOException {
    throw new NotYetImplementedForExchangeException("getOpenOrders");
  }

  /**
   * Gets the open orders
   *
   * @param params The parameters describing the filter. Note that {@link OpenOrdersParams} is an
   *     empty interface. Exchanges should implement its own params object. Params should be create
   *     with {@link #createOpenOrdersParams()}.
   * @return the open orders, null if some sort of error occurred. Implementers should log the
   *     error.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    throw new NotYetImplementedForExchangeException("getOpenOrders");
  }

  /**
   * Returns required cancel order parameter as classes
   *
   * <p>Different trading services requires different parameters for order cancellation. To provide
   * generic operation of the trade service interface. This method returns {@link Class} of the
   * parameter objects as an array. This class information can be utilized by the caller of {@link
   * #cancelOrder(CancelOrderParams)} to create instances of the required parameters such as {@link
   * CancelOrderByIdParams}, {@link CancelOrderByInstrument} etc...
   *
   * @return Class types for the required parameter classes. Default implementation returns an array
   *     with a single {@link CancelOrderByIdParams} element
   */
  default Class[] getRequiredCancelOrderParamClasses() {
    return new Class[] {CancelOrderByIdParams.class};
  }

  /** Get all openPositions of the exchange */
  default OpenPositions getOpenPositions() throws IOException {
    throw new NotYetImplementedForExchangeException();
  }

  /**
   * Place a market order
   *
   * <p>If your orders amount does to meet the restrictions dictated by {@link CurrencyPairMetaData}
   * then the exchange will reject your order. Use {@link org.knowm.xchange.utils.OrderValuesHelper}
   * to validate and / or adjust it while you'r building an order.
   *
   * @param marketOrder
   * @return the order ID
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   * @see org.knowm.xchange.utils.OrderValuesHelper
   */
  default String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotYetImplementedForExchangeException("placeMarketOrder");
  }

  /**
   * Place a limit order
   *
   * <p>If your orders amount or limit price does to meet the restrictions dictated by {@link
   * CurrencyPairMetaData} then the exchange will reject your order. Use {@link
   * org.knowm.xchange.utils.OrderValuesHelper} to validate and / or adjust those values while you'r
   * building an order.
   *
   * @param limitOrder
   * @return the order ID
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   * @see org.knowm.xchange.utils.OrderValuesHelper
   */
  default String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    throw new NotYetImplementedForExchangeException("placeLimitOrder");
  }

  /**
   * Place a stop order
   *
   * <p>If your orders amount or spot price does to meet the restrictions dictated by {@link
   * CurrencyPairMetaData} then the exchange will reject your order. Use {@link
   * org.knowm.xchange.utils.OrderValuesHelper} to validate and / or adjust those values while you'r
   * building an order.
   *
   * @param stopOrder
   * @return the order ID
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   * @see org.knowm.xchange.utils.OrderValuesHelper
   */
  default String placeStopOrder(StopOrder stopOrder) throws IOException {
    throw new NotYetImplementedForExchangeException("placeStopOrder");
  }

  /**
   * Modify or cancel/replace an existing limit order
   *
   * @implNote Some exchanges have API methods that allow to modify an order or cancel an existing
   *     one and create a new one in one request.
   *     <p>Based on exchange API there are 3 ways, how this function works:
   *     <ol>
   *       <li>Exchange supports existing order modify operation. Then function returns {@code
   *           limitOrder} order ID.
   *       <li>Exchange supports order cancel/replace by one request. Then function returns new
   *           order ID.
   *       <li>Exchange doesn't support any of these operations. Then function performs
   *           cancel/replace by two separate requests, and returns new order ID (default behavior)
   *     </ol>
   *
   * @param limitOrder Order's data to change
   * @return Order ID
   * @throws ExchangeException Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException Indication that a networking error occurred while fetching JSON data
   */
  default String changeOrder(LimitOrder limitOrder) throws IOException {
    cancelOrder(limitOrder.getId());
    return placeLimitOrder(limitOrder);
  }

  /**
   * cancels order with matching orderId (conveniance method, typical just delegate to
   * cancelOrder(CancelOrderByIdParams))
   *
   * @param orderId
   * @return true if order was successfully cancelled, false otherwise.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default boolean cancelOrder(String orderId) throws IOException {
    return cancelOrder(new DefaultCancelOrderParamId(orderId));
  }

  /**
   * cancels order with matching orderParams
   *
   * @param orderParams
   * @return true if order was successfully cancelled, false otherwise.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    throw new NotYetImplementedForExchangeException("cancelOrder");
  }

  default Collection<String> cancelAllOrders(CancelAllOrders orderParams) throws IOException {
    throw new NotYetImplementedForExchangeException("cancelAllOpenOrders");
  }

  /**
   * Fetch the history of user trades.
   *
   * <p>If you are calling this method for single exchange, known at the development time, you may
   * pass an object of specific *TradeHistoryParam class that is nested it that exchange's trade
   * service.
   *
   * <p>If, however, you are fetching user trade history from many exchanges using the same code,
   * you will find useful to create the parameter object with {@link #createTradeHistoryParams()}
   * and check which parameters are required or supported using instanceof operator. See
   * subinterfaces of {@link TradeHistoryParams}. Note that whether an interface is required or
   * supported will vary from exchange to exchange and it's described only through the javadoc.
   *
   * <p>There is also implementation of all the common interfaces, {@link TradeHistoryParamsAll} ,
   * that, with all properties set non-null, should work with any exchange.
   *
   * <p>Some exchanges allow extra parameters, not covered by any common interface. To access them,
   * you will have to use the object returned by {@link #createTradeHistoryParams()} and cast it to
   * the exchange-specific type.
   *
   * @param params The parameters describing the filter. Note that {@link TradeHistoryParams} is an
   *     empty interface. Exact set of interfaces that are required or supported by this method is
   *     described by the type of object returned from {@link #createTradeHistoryParams()} and the
   *     javadoc of the method.
   * @return UserTrades as returned by the exchange API
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   * @see #createTradeHistoryParams()
   * @see TradeHistoryParamsAll
   */
  default UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    throw new NotYetImplementedForExchangeException("getTradeHistory");
  }

  /**
   * Create {@link TradeHistoryParams} object specific to this exchange. Object created by this
   * method may be used to discover supported and required {@link
   * #getTradeHistory(TradeHistoryParams)} parameters and should be passed only to the method in the
   * same class as the createTradeHistoryParams that created the object.
   */
  default TradeHistoryParams createTradeHistoryParams() {
    throw new NotYetImplementedForExchangeException("createTradeHistoryParams");
  }

  /**
   * Create {@link OpenOrdersParams} object specific to this exchange. Object created by this method
   * may be used to discover supported and required {@link #getOpenOrders(OpenOrdersParams)}
   * parameters and should be passed only to the method in the same class as the
   * createOpenOrdersParams that created the object.
   */
  default OpenOrdersParams createOpenOrdersParams() {
    throw new NotYetImplementedForExchangeException("createOpenOrdersParams");
  }

  /**
   * Verify the order against the exchange meta data. Most implementations will require that {@link
   * org.knowm.xchange.Exchange#remoteInit()} be called before this method
   */
  default void verifyOrder(LimitOrder limitOrder) {
    throw new NotYetImplementedForExchangeException("verifyOrder");
  }

  /**
   * Verify the order against the exchange meta data. Most implementations will require that {@link
   * org.knowm.xchange.Exchange#remoteInit()} be called before this method
   */
  default void verifyOrder(MarketOrder marketOrder) {
    throw new NotYetImplementedForExchangeException("verifyOrder");
  }

  /**
   * get's the latest order form the order book that with matching orderId
   *
   * @return the order as it is on the exchange.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Collection<Order> getOrder(String... orderIds) throws IOException {
    return getOrder(toOrderQueryParams(orderIds));
  }

  static OrderQueryParams[] toOrderQueryParams(String... orderIds) {
    OrderQueryParams[] res = new OrderQueryParams[orderIds.length];
    for (int i = 0; i < orderIds.length; i++) {
      String orderId = orderIds[i];
      res[i] = new DefaultQueryOrderParam(orderId);
    }
    return res;
  }

  static String[] toOrderIds(OrderQueryParams... orderQueryParams) {
    String[] orderIds = new String[orderQueryParams.length];
    int index = 0;
    for (OrderQueryParams orderQueryParam : orderQueryParams) {
      orderIds[index++] = orderQueryParam.getOrderId();
    }
    return orderIds;
  }

  /**
   * Returns required get order parameter as classes
   *
   * <p>Different trading services requires different parameters for order querying. To provide
   * generic operation of the trade service interface, This method returns {@link Class} of the
   * parameter objects as an array. This class information can be utilized by the caller of {@link
   * #getOrder(OrderQueryParams...)} to create instances of the required parameter such as {@link
   * org.knowm.xchange.service.trade.params.orders.OrderQueryParamCurrencyPair}, {@link
   * org.knowm.xchange.service.trade.params.orders.OrderQueryParamInstrument} etc...
   *
   * @return Class type for the required parameter class. Default implementation returns an instance
   *     of {@link OrderQueryParams} element
   */
  default Class getRequiredOrderQueryParamClass() {
    return OrderQueryParams.class;
  }

  /**
   * get's the latest order form the order book that with matching orderQueryParams
   *
   * @return the order as it is on the exchange.
   * @throws ExchangeException - Indication that the exchange reported some kind of error with the
   *     request or response
   * @throws NotAvailableFromExchangeException - Indication that the exchange does not support the
   *     requested function or data
   * @throws NotYetImplementedForExchangeException - Indication that the exchange supports the
   *     requested function or data, but it has not yet been implemented
   * @throws IOException - Indication that a networking error occurred while fetching JSON data
   */
  default Collection<Order> getOrder(OrderQueryParams... orderQueryParams) throws IOException {
    throw new NotAvailableFromExchangeException("getOrder");
  }
}
