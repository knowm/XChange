/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.service.streaming;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.BaseExchangeService;
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
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BaseWebSocketExchangeService(ExchangeSpecification exchangeSpecification, ExchangeStreamingConfiguration exchangeStreamingConfiguration) {

    super(exchangeSpecification);
    reconnectService = new ReconnectService(this, exchangeStreamingConfiguration);
  }

  protected synchronized void internalConnect(URI uri, ExchangeEventListener exchangeEventListener, Map<String, String> headers) {

    log.debug("internalConnect");

    // Validate inputs
    Assert.notNull(exchangeEventListener, "runnableExchangeEventListener cannot be null");

    try {
      log.debug("Attempting to open a websocket against {}", uri);
      this.exchangeEventProducer = new WebSocketEventProducer(uri.toString(), exchangeEventListener, headers);
      exchangeEventProducer.connect();
    } catch (URISyntaxException e) {
      throw new ExchangeException("Failed to open websocket!", e);
    }

  }

  @Override
  public synchronized void disconnect() {

    if (exchangeEventProducer != null) {
      exchangeEventProducer.close();
    }
    log.debug("disconnect() called");
  }

  @Override
  public ExchangeEvent getNextEvent() throws InterruptedException {

    ExchangeEvent event = consumerEventQueue.take();

    if (reconnectService != null) { // logic here to intercept errors and reconnect..
      reconnectService.intercept(event);
    }
    return event;
  }

  @Override
  public void send(String msg) {

    exchangeEventProducer.send(msg);
  }

}
