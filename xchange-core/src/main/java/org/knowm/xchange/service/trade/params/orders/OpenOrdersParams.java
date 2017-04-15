package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.service.trade.TradeService;

/**
 * Root interface for all interfaces used as a parameter type for {@link TradeService#getOpenOrders(OpenOrdersParams)}.
 * <p>
 * Each exchange should have its own class implementing at least one from following available interfaces:
 * <ul>
 * <li>{@link OpenOrdersParamCurrencyPair}.</li>
 * </ul>
 * <p>
 * When suitable exchange params definition can extend from default classes, eg. {@link DefaultOpenOrdersParamCurrencyPair}.
 */
public interface OpenOrdersParams {
}
