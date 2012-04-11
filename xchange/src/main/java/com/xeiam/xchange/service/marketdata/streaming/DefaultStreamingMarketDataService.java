package com.xeiam.xchange.service.marketdata.streaming;

import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * <p>Streaming market data service to provide the following to streaming market data API:</p>
 * <ul>
 * <li>Connection to an upstream market data source through a direct socket connection</li>
 * <li>Provision of a basic byte[] based market data event producer on a fixed schedule</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class DefaultStreamingMarketDataService implements StreamingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(DefaultStreamingMarketDataService.class);

  private final String host;
  private final int port;
  private final ScheduledExecutorService executorService;

  /**
   * A direct socket connection only requires a host and a port (no protocol)
   * @param host The host name
   * @param port The port to connect to
   */
  public DefaultStreamingMarketDataService(String host, int port) {
    Assert.notNull(host, "host cannot be null");
    this.host = host;
    this.port = port;

    executorService = Executors.newSingleThreadScheduledExecutor();

  }

  @Override
  public synchronized MarketDataListener registerMarketDataListener(MarketDataListener marketDataListener) {

    log.debug("Registering MarketDataListener for '{}:{}'",host,port);

    // Create a queue for a producer-consumer pair
    BlockingQueue<MarketDataEvent> marketDataEvents = new ArrayBlockingQueue<MarketDataEvent>(1024);

    // Schedule market data event producer
    // TODO Consider refactoring to inject this producer via an extensible getter with constructor
    // (this will decouple this
    executorService.scheduleAtFixedRate(new SocketMarketDataEventProducer(host,port,marketDataEvents), 0L, 10L, TimeUnit.SECONDS);

    marketDataListener.setMarketDataEventQueue(marketDataEvents);

    return marketDataListener;
  }

  @Override
  public synchronized void unregisterMarketDataListener(MarketDataListener marketDataListener) {
    // TODO Implement this
//    if (!marketDataListeners.remove(runnableMarketDataListener)) {
//      throw new IllegalArgumentException("Unknown marketDataListener - possible coding error. " + runnableMarketDataListener.toString());
//    }
  }

}
