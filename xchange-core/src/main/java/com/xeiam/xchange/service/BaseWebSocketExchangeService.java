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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

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

  private final ExecutorService eventExecutorService = Executors.newFixedThreadPool(2);

  /**
   * The event queue for the producer
   */
  private final BlockingQueue<ExchangeEvent> producerEventQueue = new LinkedBlockingQueue<ExchangeEvent>(1024);

  /**
   * The event queue for the consumer
   */
  protected final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>(1024);

  protected ReconnectService reconnectService;

  /**
   * The exchange event producer
   */
  private WebSocketEventProducer exchangeEventProducer;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public BaseWebSocketExchangeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    reconnectService = new ReconnectService(this);
  }

  protected synchronized void internalConnect(URI uri, RunnableExchangeEventListener runnableExchangeEventListener) {

    log.info("Connecting...");

    // Validate inputs
    Assert.notNull(runnableExchangeEventListener, "runnableExchangeEventListener cannot be null");

    // Validate state
    if (eventExecutorService.isShutdown()) {
      throw new IllegalStateException("Service has been stopped. Create a new one rather than reuse a reference.");
    }

    try {
      log.debug("Attempting to open a websocket against {}", uri);
      this.exchangeEventProducer = new WebSocketEventProducer(uri.toString(), producerEventQueue);
      exchangeEventProducer.connect();
    } catch (URISyntaxException e) {
      throw new ExchangeException("Failed to open websocket!", e);
    }

    // Configure the exchange event listener event queue
    runnableExchangeEventListener.setExchangeEventQueue(producerEventQueue);

    // Submit the event threads to their services
    eventExecutorService.submit(runnableExchangeEventListener);

    log.info("Socket connected OK. Check queues for events.");

  }

  @Override
  public synchronized void disconnect() {

    if (!eventExecutorService.isShutdown()) {
      // We close on the socket to get an immediate result
      // otherwise the producer would block until the exchange
      // sent a message which could be forever
      if (exchangeEventProducer != null) {
        exchangeEventProducer.close();
      }
    }
    eventExecutorService.shutdownNow();
    log.debug("Stopped");
  }

  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {

    ExchangeEvent event = consumerEventQueue.take();

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      reconnectService.intercept(event);
    }

    return event;

  }
}
