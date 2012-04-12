package com.xeiam.xchange.service.marketdata.streaming;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Abstract base class to provide the following to XChange clients:
 * </p>
 * <ul>
 * <li>Simple extension point for a {@link Runnable} designed for use with an ExecutorService</li>
 * </ul>
 * Example:<br>
 * 
 * <pre>
 * </pre>
 * 
 * @since 0.0.1
 */
public abstract class RunnableMarketDataListener implements MarketDataListener, Runnable {

  private final Logger log = LoggerFactory.getLogger(RunnableMarketDataListener.class);

  private BlockingQueue<MarketDataEvent> marketDataEvents;

  /**
   * Constructor
   */
  RunnableMarketDataListener() {
  }

  @Override
  public void run() {
    try {
      // Block until an event occurs
      handleEvent(marketDataEvents.take());
    } catch (InterruptedException e) {
      log.warn(e.getMessage(), e);
    }
  }

  @Override
  public void setMarketDataEventQueue(BlockingQueue<MarketDataEvent> marketDataEvents) {
    this.marketDataEvents = marketDataEvents;
  }

  /**
   * <p>
   * Client code is expected to implement this in a manner specific to their own application
   * </p>
   * 
   * @param event The market data event containing the information
   */
  public abstract void handleEvent(MarketDataEvent event);
}
