package com.xeiam.xchange.service.marketdata.streaming;

import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * Listener to provide the following to API:
 * </p>
 * <ul>
 * <li>Callback methods for market data events</li>
 * </ul>
 * <p>
 * A {@link MarketDataListener} is normally executed in a client thread using an executor service and to facilitate this the {@link RunnableMarketDataListener} is provided as an extension point
 * </p>
 * 
 * @since 0.0.1
 */
public interface MarketDataListener {

  /**
   * @param marketDataEvents The blocking queue that links the XChange thread pool to the client thread pool
   */
  void setMarketDataEventQueue(BlockingQueue<MarketDataEvent> marketDataEvents);

}
