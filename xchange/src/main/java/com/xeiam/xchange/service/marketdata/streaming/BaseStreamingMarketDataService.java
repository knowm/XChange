package com.xeiam.xchange.service.marketdata.streaming;

import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Base class to provide the following to streaming market data:</p>
 * <ul>
 * <li>Provision of standard support code</li>
 * </ul>
 *
 * @since 0.0.1
 *
 */
public abstract class BaseStreamingMarketDataService implements StreamingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(BaseStreamingMarketDataService.class);

  protected final Set<MarketDataListener> marketDataListeners = new HashSet<MarketDataListener>();

  protected final String uri;
  protected final String host;
  protected final int port;

  /**
   * A SocketIO or WebSocket connection requires a URI
   * @param uri The URI
   */
  public BaseStreamingMarketDataService(String uri) {
    Assert.notNull(uri, "URI cannot be null");
    this.host = null;
    this.port = 0;
    this.uri = null;
  }

  /**
   * A direct socket connection only requires a host and a port (no protocol)
   * @param host The host name 
   * @param port The port to connect to
   */
  public BaseStreamingMarketDataService(String host, int port) {
    Assert.notNull(host, "host cannot be null");
    this.host = host;
    this.port = port;
    this.uri = null;
  }
  

  @Override
  public synchronized void addListener(MarketDataListener marketDataListener) {
    Assert.notNull(marketDataListener, "marketDataListener cannot be null");
    if (!marketDataListeners.add(marketDataListener)) {
      throw new IllegalArgumentException("Duplicated marketDataListener - possible coding error. " + marketDataListener.toString());
    }
  }

  @Override
  public synchronized void removeListener(MarketDataListener marketDataListener) {
    if (!marketDataListeners.remove(marketDataListener)) {
      throw new IllegalArgumentException("Unknown marketDataListener - possible coding error. " + marketDataListener.toString());
    }
  }

  @Override
  public void fireMarketDataEvent(MarketDataEvent marketDataEvent) {

    // Prevent concurrent updates against the listener collection
    synchronized (marketDataListeners) {
      if (marketDataListeners != null && !marketDataListeners.isEmpty()) {

        for (MarketDataListener marketDataListener : marketDataListeners) {
          // Allow the listener to perform its work and manage external programming errors through removal
          synchronized (marketDataListener) {
            try {
              marketDataListener.onUpdate(marketDataEvent);
            } catch (RuntimeException e) {
              log.warn("Unexpected exception in market data listener. Removing from listeners.", e);
              removeListener(marketDataListener);
            }
          }
        }
      }

    }
  }
  
  public abstract void connect();
  
  public abstract void disconnect();
}