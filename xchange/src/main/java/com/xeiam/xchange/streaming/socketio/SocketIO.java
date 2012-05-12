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
/*
 * socket.io-java-client SocketIO.java
 *
 * Copyright (c) 2011, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.utils.Assert;

/**
 * The Class SocketIO.
 */
public class SocketIO {

  private final Logger log = LoggerFactory.getLogger(SocketIO.class);

  /**
   * callback of this Socket.
   */
  private final IOCallback callback;

  /**
   * connection of this Socket.
   */
  private IOConnection connection;

  private String namespace;

  private final URL url;

  /**
   * Constructor Instantiates a new socket.io connection.
   */
  public SocketIO(final String url, final IOCallback callback) throws MalformedURLException {

    Assert.notNull(url, "url cannot be null");
    this.url = new URL(url);
    Assert.notNull(callback, "callback cannot be null");
    this.callback = callback;
    connect();
  }

  /**
   * connects to supplied host
   */
  public void connect() {

    log.debug("connect()");

    final String origin = this.url.getProtocol() + "://" + this.url.getAuthority();
    this.namespace = this.url.getPath();
    if (this.namespace.equals("/")) {
      this.namespace = "";
    }
    this.connection = IOConnection.register(origin, this);
  }

  /**
   * Emits an event to the Socket.IO server. If the connection is not established, the call will be buffered and sent as soon as it is possible.
   * 
   * @param event the event name
   * @param args the arguments
   */
  public void emit(final String event, final Object... args) {
    this.connection.emit(this, event, null, args);
  }

  /**
   * Emits an event to the Socket.IO server. If the connection is not established, the call will be buffered and sent as soon as it is possible.
   * 
   * @param event the event name
   * @param ack an acknowledge implementation
   * @param args the arguments
   */
  public void emit(final String event, IOAcknowledge ack, final Object... args) {
    this.connection.emit(this, event, ack, args);
  }

  /**
   * Gets the callback. Internally used.
   * 
   * @return the callback
   */
  public IOCallback getCallback() {
    return this.callback;
  }

  /**
   * Gets the namespace. Internally used.
   * 
   * @return the namespace
   */
  public String getNamespace() {
    return this.namespace;
  }

  /**
   * Send JSON data to the Socket.io server.
   * 
   * @param json the JSON object
   */
  public void send(final JSONObject json) {
    this.connection.send(this, null, json);
  }

  /**
   * Send JSON data to the Socket.io server.
   * 
   * @param ack an acknowledge implementation
   * @param json the JSON object
   */
  public void send(IOAcknowledge ack, final JSONObject json) {
    this.connection.send(this, ack, json);
  }

  /**
   * Send String data to the Socket.io server.
   * 
   * @param message the message String
   */
  public void send(final String message) {
    this.connection.send(this, null, message);
  }

  /**
   * Send JSON data to the Socket.io server.
   * 
   * @param ack an acknowledge implementation
   * @param message the message String
   */
  public void send(IOAcknowledge ack, final String message) {
    this.connection.send(this, ack, message);
  }

  /**
   * Disconnect the socket.
   */
  public void disconnect() {
    this.connection.unregister(this);
  }

  /**
   * Triggers the transport to reconnect. This had become useful on some android devices which do not shut down tcp-connections when switching from HSDPA to Wifi
   */
  public void reconnect() {
    this.connection.reconnect();
  }

  /**
   * Returns, if a connection is established at the moment
   * 
   * @return true if a connection is established, false if the transport is not connected or currently connecting
   */
  public boolean isConnected() {
    return this.connection.isConnected();
  }

  /**
   * Returns the name of the used transport
   * 
   * @return the name of the currently used transport
   */
  public String getTransport() {
    IOTransport transport = this.connection.getTransport();
    return transport != null ? transport.getName() : null;
  }
}
