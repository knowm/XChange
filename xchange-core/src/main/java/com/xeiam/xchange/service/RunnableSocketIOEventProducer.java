/**
 * Copyright (C) 2012 Xeiam LLC http://xeiam.com
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

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.streaming.socketio.IOAcknowledge;
import com.xeiam.xchange.streaming.socketio.IOCallback;
import com.xeiam.xchange.streaming.socketio.SocketIO;
import com.xeiam.xchange.streaming.socketio.SocketIOException;

/**
 * <p>
 * Producer to provide the following to {@link ExchangeEventListener}:
 * </p>
 * <ul>
 * <li>Raw exchange data from the upstream server</li>
 * </ul>
 */
public class RunnableSocketIOEventProducer implements RunnableExchangeEventProducer, IOCallback {

  private final Logger log = LoggerFactory.getLogger(RunnableSocketIOEventProducer.class);

  private final BlockingQueue<ExchangeEvent> queue;
  private final SocketIO socketIO;

  /**
   * Constructor
   * 
   * @param socket The underlying socketio to use
   * @param queue The exchange event queue for the producer to work against
   */
  RunnableSocketIOEventProducer(SocketIO socketClient, BlockingQueue<ExchangeEvent> queue) {

    this.queue = queue;
    this.socketIO = socketClient;
  }

  @Override
  public void run() {

  }

  @Override
  public void onConnect() {

    log.debug("onConnect");

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        String message = "{\"message\":\"connected\"}";
        return message.getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }

  }

  @Override
  public void onMessage(final String data, IOAcknowledge ack) {

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        return data.getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }
  }

  @Override
  public void onMessage(final JSONObject json, IOAcknowledge ack) {

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        return json.toString().getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }

  }

  @Override
  public void onDisconnect() {

    log.debug("onDisconnect");

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        String message = "{\"message\":\"disconnected\"}";
        return message.getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }

  }

  @Override
  public void on(final String event, IOAcknowledge ack, Object... args) {

    log.debug("on: " + event);

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        String message = "{\"message\":\"" + event + "\"}";
        return message.getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }

  }

  @Override
  public void onError(SocketIOException socketIOException) {

    log.error("onError: " + socketIOException.getMessage());

    ExchangeEvent exchangeEvent = new ExchangeEvent() {

      @Override
      public byte[] getRawData() {

        String message = "{\"message\":\"SocketIOException\"}";
        return message.getBytes();
      }
    };
    try {
      queue.put(exchangeEvent);
    } catch (InterruptedException e) {
      log.warn("InterruptedException occurred while adding ExchangeEvent to Queue!");
    }

    socketIO.reconnect();

  }
}
