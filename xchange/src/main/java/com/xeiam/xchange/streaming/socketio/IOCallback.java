/**
 * Copyright (c) 2012, Enno Boland
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
package com.xeiam.xchange.streaming.socketio;

import org.json.JSONObject;

/**
 * The Interface IOCallback. A callback interface to SocketIO
 */
public interface IOCallback {

  /**
   * On disconnect. Called when the socket disconnects and there are no further attempts to reconnect
   */
  void onDisconnect();

  /**
   * On connect. Called when the socket becomes ready so it is now able to receive data
   */
  void onConnect();

  /**
   * On message. Called when the server sends String data.
   * 
   * @param data the data.
   * @param ack an {@link IOAcknowledge} instance, may be <code>null</code> if there's none
   */
  void onMessage(String data, IOAcknowledge ack);

  /**
   * On message. Called when the server sends JSON data.
   * 
   * @param json JSON object sent by server.
   * @param ack an {@link IOAcknowledge} instance, may be <code>null</code> if there's none
   */
  void onMessage(JSONObject json, IOAcknowledge ack);

  /**
   * On [Event]. Called when server emits an event.
   * 
   * @param event Name of the event
   * @param ack an {@link IOAcknowledge} instance, may be <code>null</code> if there's none
   * @param args Arguments of the event
   */
  void on(String event, IOAcknowledge ack, Object... args);

  /**
   * On error. Called when socket is in an undefined state. No reconnect attempts will be made.
   * 
   * @param socketIOException the last exception describing the error
   */
  void onError(SocketIOException socketIOException);
}
