package com.xeiam.xchange.service.marketdata.streaming;

/**
 * <p>Interface to provide the following to market data event producers:</p>
 * <ul>
 * <li>Provides the intention to use a thread to obtain market data</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface RunnableMarketDataEventProducer extends MarketDataEventProducer, Runnable {

}
