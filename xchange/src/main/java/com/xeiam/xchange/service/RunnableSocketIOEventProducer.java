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
 * <li>Raw market data from the upstream server</li>
 * </ul>
 */
public class RunnableSocketIOEventProducer implements RunnableExchangeEventProducer, IOCallback {

  private final Logger log = LoggerFactory.getLogger(RunnableSocketIOEventProducer.class);

  private final BlockingQueue<ExchangeEvent> queue;
  private final SocketIO socketClient;
  private final boolean stopRequested = false;

  /**
   * Package constructor
   * 
   * @param socket The underlying socket to use (operates in a
   * @param queue The market data event queue for the producer to work against
   */
  RunnableSocketIOEventProducer(SocketIO socketClient, BlockingQueue<ExchangeEvent> queue) {
    this.queue = queue;
    this.socketClient = socketClient;
  }

  @Override
  public void run() {

    // BufferedReader in = null;
    //
    // if (stopRequested) {
    // return;
    // }
    //
    // try {
    //
    // in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    //
    // // Do this forever
    // while (true) {
    // // The readLine() will be interrupted by the external thread managing the socket
    // final String data = in.readLine();
    // log.debug("Received data '{}'", data);
    //
    // // Create an event
    // ExchangeEvent marketDataEvent = new ExchangeEvent() {
    // @Override
    // public byte[] getRawData() {
    // return data.getBytes();
    // }
    // };
    // queue.put(marketDataEvent);
    // }
    //
    // } catch (IOException e) {
    // log.warn(e.getMessage(), e);
    // } catch (InterruptedException e) {
    // // Cannot put onto the queue we're probably shutting down
    // log.debug("Closing producer due to interrupt.");
    // } finally {
    // if (in != null) {
    // try {
    // in.close();
    // } catch (IOException e1) {
    // log.warn(e1.getMessage(), e1);
    // }
    // }
    // if (socket != null) {
    // try {
    // socket.close();
    // } catch (IOException e1) {
    // log.warn(e1.getMessage(), e1);
    // }
    // }
    //
    // }
  }

  @Override
  public void onDisconnect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onConnect() {
    // TODO Auto-generated method stub

  }

  @Override
  public void onMessage(String data, IOAcknowledge ack) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onMessage(JSONObject json, IOAcknowledge ack) {
    // TODO Auto-generated method stub

  }

  @Override
  public void on(String event, IOAcknowledge ack, Object... args) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onError(SocketIOException socketIOException) {
    // TODO Auto-generated method stub

  }
}
