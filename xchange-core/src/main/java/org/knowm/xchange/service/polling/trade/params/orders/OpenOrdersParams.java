package org.knowm.xchange.service.polling.trade.params.orders;

/**
 * Root interface for all interfaces used as a parameter type for
 * {@link org.knowm.xchange.service.polling.trade.PollingTradeService#getOpenOrders(OpenOrdersParams)}.
 * <p>
 * Each exchange should have its own class implementing at least one from following available interfaces:
 * <ul>
 *   <li>{@link OpenOrdersParamCurrencyPair}.</li>
 * </ul>
 * <p>
 * When suitable exchange params definition can extend from default classes, eg. {@link DefaultOpenOrdersParamCurrencyPair}.
 */
public interface OpenOrdersParams {
}
