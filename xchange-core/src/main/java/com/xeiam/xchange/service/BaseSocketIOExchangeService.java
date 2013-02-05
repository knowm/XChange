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
import com.xeiam.xchange.streaming.socketio.SocketIO;
import com.xeiam.xchange.utils.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <p>
 * Socket IO exchange service to provide the following to streaming market data API:
 * </p>
 * <ul>
 * <li>Connection to an upstream exchange data source with a configured provider</li>
 * </ul>
 */
public abstract class BaseSocketIOExchangeService extends BaseExchangeService implements StreamingExchangeService {

  private final Logger log = LoggerFactory.getLogger(BaseSocketIOExchangeService.class);

  private final ExecutorService eventExecutorService = Executors.newFixedThreadPool(2);

  /**
   * The event queue for the producer
   */
  private final BlockingQueue<ExchangeEvent> producerEventQueue = new LinkedBlockingQueue<ExchangeEvent>(1024);

  /**
   * The event queue for the consumer
   */
  protected final BlockingQueue<ExchangeEvent> consumerEventQueue = new LinkedBlockingQueue<ExchangeEvent>(1024);

  protected SocketIO socketIO;

  /**
   * The exchange event producer
   */
  private RunnableExchangeEventProducer runnableExchangeEventProducer;

  /**
   * Constructor
   *
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public BaseSocketIOExchangeService(ExchangeSpecification exchangeSpecification) throws IOException {

    super(exchangeSpecification);
  }

  /**
   * Handles the actual connection process
   *
   * @param uri                           The URI of the upstream server
   * @param runnableExchangeEventListener The event listener
   */
  protected synchronized void internalConnect(URI uri, RunnableExchangeEventListener runnableExchangeEventListener) {

    log.info("Connecting...");

    // Validate inputs
    Assert.notNull(runnableExchangeEventListener, "runnableMarketDataListener cannot be null");

    // Validate state
    if (eventExecutorService.isShutdown()) {
      throw new IllegalStateException("Service has been stopped. Create a new one rather than reuse a reference.");
    }

    try {
      log.debug("Attempting to open a socketIO against {}:{}", uri, exchangeSpecification.getPort());
      this.runnableExchangeEventProducer = new RunnableSocketIOEventProducer(producerEventQueue);
      this.socketIO = new SocketIO(uri.toString(), (RunnableSocketIOEventProducer) runnableExchangeEventProducer);
    } catch (IOException e) {
      throw new ExchangeException("Failed to open socket!", e);
    }

    // Configure the exchange event listener event queue
    runnableExchangeEventListener.setExchangeEventQueue(producerEventQueue);

    // Submit the event threads to their services
    eventExecutorService.submit(runnableExchangeEventProducer);
    eventExecutorService.submit(runnableExchangeEventListener);

    log.info("Socket connected OK. Check queues for events.");
  }

  @Override
  public void send(String message) {

    this.socketIO.send(message);
  }

  @Override
  public synchronized void disconnect() {

    if (!eventExecutorService.isShutdown()) {
      // We close on the socket to get an immediate result
      // otherwise the producer would block until the exchange
      // sent a message which could be forever
      if (socketIO != null) {
        socketIO.disconnect();
      }
    }
    eventExecutorService.shutdownNow();
    log.debug("Stopped");
  }

  @Override
  public RunnableExchangeEventProducer getRunnableExchangeEventProducer() {

    return runnableExchangeEventProducer;
  }

  @Override
  public void setRunnableExchangeEventProducer(RunnableExchangeEventProducer runnableMarketDataEventProducer) {

    this.runnableExchangeEventProducer = runnableMarketDataEventProducer;
  }

  public BlockingQueue<ExchangeEvent> getEventQueue() {

    return consumerEventQueue;

  }
}
