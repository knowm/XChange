package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.TradeService;

/**
 * Root interface for all interfaces used as a parameter type for {@link
 * TradeService#getOpenOrders(OpenOrdersParams)}. Each exchange should have its own class
 * implementing at least one from following available interfaces:
 *
 * <ul>
 *   <li>{@link OpenOrdersParamCurrencyPair}.
 * </ul>
 *
 * When suitable exchange params definition can extend from default classes, eg. {@link
 * DefaultOpenOrdersParamCurrencyPair}.
 */
public interface OpenOrdersParams {
  /**
   * Checks if passed order is suitable for open orders params. May be used for XChange side orders
   * filtering
   *
   * @param order The order to filter.
   * @return true if order is ok
   */
  boolean accept(LimitOrder order);

  /**
   * Added later, this method allows the filter to also to apply to stop orders, at a small cost. It
   * should be explicitly implemented for better performance.
   *
   * @param order The order to filter.
   * @return true if order is ok.
   */
  default boolean accept(Order order) {
    return accept(LimitOrder.Builder.from(order).build());
  }
}
