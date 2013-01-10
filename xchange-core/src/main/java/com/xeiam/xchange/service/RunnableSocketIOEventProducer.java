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

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.streaming.socketio.IOAcknowledge;
import com.xeiam.xchange.streaming.socketio.IOCallback;
import com.xeiam.xchange.streaming.socketio.SocketIOException;

/**
 * <p>
 * Producer to provide the following to {@link ExchangeEventListener}:
 * </p>
 * <ul>
 * <li>Raw exchange data from the upstream server</li>
 * <li>Provision of event type to allow consumers to handle messages efficiently</li>
 * </ul>
 */
public class RunnableSocketIOEventProducer implements RunnableExchangeEventProducer, IOCallback {

  private final Logger log = LoggerFactory.getLogger(RunnableSocketIOEventProducer.class);

  private final BlockingQueue<ExchangeEvent> queue;

  /**
   * Constructor
   * 
   * @param queue The exchange event queue for the producer to work against
   */
  RunnableSocketIOEventProducer(BlockingQueue<ExchangeEvent> queue) {

    this.queue = queue;
  }

  @Override
  public void run() {

  }

  @Override
  public void onConnect() {

    log.debug("onConnect");

    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.CONNECT, "connected");

    addToQueue(exchangeEvent);

  }

  @Override
  public void onMessage(final String data, IOAcknowledge ack) {

    ExchangeEvent exchangeEvent = new DefaultExchangeEvent(ExchangeEventType.MESSAGE, data.getBytes());

    addToQueue(exchangeEvent);
  }

  @Override
  public void onDisconnect() {

    log.debug("onDisconnect");

    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.DISCONNECT, "disconnected");

    addToQueue(exchangeEvent);

  }

  @Override
  public void on(final String event, IOAcknowledge ack, Object... args) {

    log.debug("on: " + event);

    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.EVENT, event);

    addToQueue(exchangeEvent);

  }

  @Override
  public void onError(SocketIOException socketIOException) {

    log.error("onError: {}", socketIOException.getMessage(), socketIOException);

    ExchangeEvent exchangeEvent = new JsonWrappedExchangeEvent(ExchangeEventType.ERROR, socketIOException.getMessage());

    addToQueue(exchangeEvent);

  }

  private void addToQueue(ExchangeEvent exchangeEvent) {

    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!", e);
    }
  }
}
