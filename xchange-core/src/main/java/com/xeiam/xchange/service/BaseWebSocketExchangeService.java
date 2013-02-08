/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.service;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
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
 */
public abstract class BaseWebSocketExchangeService extends BaseExchangeService implements StreamingExchangeService {

  private final Logger log = LoggerFactory.getLogger(BaseWebSocketExchangeService.class);

  private final ExecutorService executorService;
  private final BlockingQueue<ExchangeEvent> marketDataEvents = new ArrayBlockingQueue<ExchangeEvent>(1024);

  private Socket socket;
  private RunnableExchangeEventProducer runnableExchangeEventProducer = null;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public BaseWebSocketExchangeService(ExchangeSpecification exchangeSpecification) throws IOException {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getHost(), "host cannot be null");
    executorService = Executors.newSingleThreadExecutor();
  }

  protected synchronized void internalConnect(URI uri, RunnableExchangeEventListener runnableExchangeEventListener) {

    // Validate inputs
    Assert.notNull(runnableExchangeEventListener, "runnableExchangeEventListener cannot be null");

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
    this.runnableExchangeEventProducer = new RunnableWebSocketEventProducer(socket, marketDataEvents);

    runnableExchangeEventListener.setExchangeEventQueue(marketDataEvents);
    executorService.submit(runnableExchangeEventProducer);

    log.debug("Started OK");

  }

  @Override
  public void send(String message) {

    // TODO Auto-generated method stub

  }

  @Override
  public synchronized void disconnect() {

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
  public RunnableExchangeEventProducer getRunnableExchangeEventProducer() {

    return runnableExchangeEventProducer;
  }

  @Override
  public void setRunnableExchangeEventProducer(RunnableExchangeEventProducer runnableMarketDataEventProducer) {

    this.runnableExchangeEventProducer = runnableMarketDataEventProducer;
  }
}
