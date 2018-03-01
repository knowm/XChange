package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.service.trade.TradeService;

/**
 * Root interface for all interfaces used as a parameter type for {@link TradeService#getOrder(org.knowm.xchange.service.trade.params.orders.OrderQueryParams...)}.
 * <p>
 *  Exchanges should have their own implementation of this interface if querying an order requires information additional to orderId
 * <ul>
 * <li>{@link OpenOrdersParamCurrencyPair}.</li>
 * </ul>
 * <p>
 */
public interface OrderQueryParams {
    /**
     * Sets the orderId
     */
    void setOrderId(String orderId);
    String getOrderId();
}
