package com.xeiam.xchange.service.marketdata.streaming;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * Streaming market data service to provide the following to streaming market data API:
 * </p>
 * <ul>
 * <li>Connection to an upstream market data source with a configured provider</li>
 * </ul>
 *
 * @since 0.0.1
 */
public class BaseStreamingMarketDataService implements StreamingMarketDataService {

  private final Logger log = LoggerFactory.getLogger(BaseStreamingMarketDataService.class);

  private final ExecutorService executorService;
  private final ExchangeSpecification exchangeSpecification;
  private final BlockingQueue<MarketDataEvent> marketDataEvents = new ArrayBlockingQueue<MarketDataEvent>(1024);

  private Socket socket;
  private RunnableMarketDataEventProducer runnableMarketDataEventProducer = null;

  /**
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public BaseStreamingMarketDataService(ExchangeSpecification exchangeSpecification) throws IOException {

    Assert.notNull(exchangeSpecification, "exchangeSpecification cannot be null");
    Assert.notNull(exchangeSpecification.getHost(), "host cannot be null");

    this.exchangeSpecification = exchangeSpecification;

    executorService = Executors.newSingleThreadExecutor();
  }

  @Override
  public synchronized void start(RunnableMarketDataListener runnableMarketDataListener) {

    // Validate inputs
    Assert.notNull(runnableMarketDataListener,"runnableMarketDataListener cannot be null");

    // Validate state
    if (executorService.isShutdown()) {
      throw new IllegalStateException("Service has been stopped. Create a new one rather than reuse a reference.");
    }

    try {
      log.debug("Attempting to open a direct socket against {}:{}", exchangeSpecification.getHost(), exchangeSpecification.getPort());
      this.socket = new Socket(exchangeSpecification.getHost(), exchangeSpecification.getPort());
    } catch (IOException e) {
      throw new ExchangeException("Failed to open socket: " + e.getMessage(), e);
    }
    this.runnableMarketDataEventProducer = new RunnableSocketMarketDataEventProducer(socket, marketDataEvents);

    runnableMarketDataListener.setMarketDataEventQueue(marketDataEvents);
    executorService.submit(runnableMarketDataEventProducer);

    log.debug("Started OK");

  }

  @Override
  public synchronized void stop() {
    try {
      if (!executorService.isShutdown()) {
        // We close on the socket to get an immediate result
        // otherwise the producer would block until the exchange
        // sent a message which could be forever
        if (!socket.isClosed()) {
          socket.shutdownInput();
        }
      }
    } catch (IOException e) {
      log.warn("Socket encountered an error: {}", e.getMessage());
    } finally {
      executorService.shutdownNow();
      log.debug("Stopped");
    }

  }

  @Override
  public RunnableMarketDataEventProducer getRunnableMarketDataEventProducer() {
    return runnableMarketDataEventProducer;
  }

  @Override
  public void setRunnableMarketDataEventProducer(RunnableMarketDataEventProducer runnableMarketDataEventProducer) {
    this.runnableMarketDataEventProducer = runnableMarketDataEventProducer;
  }
}
