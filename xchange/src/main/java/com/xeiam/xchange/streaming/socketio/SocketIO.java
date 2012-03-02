/*
 * socket.io-java-client SocketIO.java
 *
 * Copyright (c) 2011, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The Class SocketIO.
 */
public class SocketIO {

  /**
   * callback of this Socket.
   */
  private IOCallback callback;

  /**
   * connection of this Socket.
   */
  private IOConnection connection;

  /**
   * namespace.
   */
  private String namespace;

  private URL url;

  /**
   * Instantiates a new socket.io connection. The object connects after
   * calling {@link #connect(java.net.URL, IOCallback)} or
   * {@link #connect(String, IOCallback)}
   */
  public SocketIO() {

  }

  /**
   * Instantiates a new socket.io connection. The object connects after
   * calling {@link #connect(IOCallback)}
   *
   * @param url the url
   *
   * @throws java.net.MalformedURLException the malformed url exception
   */
  public SocketIO(final String url) throws MalformedURLException {
    if (url == null)
      throw new RuntimeException("url may not be null.");
    setAndConnect(new URL(url), null);
  }

  /**
   * Instantiates a new socket.io object and connects to the given url. Do not
   * call any of the connect() methods afterwards.
   *
   * @param url      the url
   * @param callback the callback
   *
   * @throws java.net.MalformedURLException the malformed url exception
   */
  public SocketIO(final String url, final IOCallback callback)
    throws MalformedURLException {
    connect(url, callback);
  }

  /**
   * Instantiates a new socket.io object and connects to the given url. Do not
   * call any of the connect() methods afterwards.
   *
   * @param url      the url
   * @param callback the callback
   */
  public SocketIO(final URL url, final IOCallback callback) {
    if (setAndConnect(url, callback) == false)
      throw new RuntimeException("url and callback may not be null.");
  }

  /**
   * Instantiates a new socket.io connection. The object connects after
   * calling {@link #connect(IOCallback)}
   *
   * @param url the url
   */
  public SocketIO(final URL url) {
    setAndConnect(url, null);
  }

  /**
   * connects to supplied host using callback. Do only use this method if you
   * instantiate {@link com.xeiam.xchange.streaming.socketio.SocketIO} using {@link #SocketIO()}.
   *
   * @param url      the url
   * @param callback the callback
   */
  public void connect(final String url, final IOCallback callback)
    throws MalformedURLException {
    if (setAndConnect(new URL(url), callback) == false) {
      if (url == null || callback == null)
        throw new RuntimeException("url and callback may not be null.");
      else
        throw new RuntimeException(
          "connect(String, IOCallback) can only be invoked after SocketIO()");
    }
  }

  /**
   * connects to supplied host using callback. Do only use this method if you
   * instantiate {@link com.xeiam.xchange.streaming.socketio.SocketIO} using {@link #SocketIO()}.
   *
   * @param url      the url
   * @param callback the callback
   */
  public void connect(URL url, IOCallback callback) {
    if (setAndConnect(url, callback) == false) {
      if (url == null || callback == null)
        throw new RuntimeException("url and callback may not be null.");
      else
        throw new RuntimeException(
          "connect(URL, IOCallback) can only be invoked after SocketIO()");
    }
  }

  /**
   * connects to an already set host. Do only use this method if you
   * instantiate {@link com.xeiam.xchange.streaming.socketio.SocketIO} using {@link #SocketIO(String)} or
   * {@link #SocketIO(java.net.URL)}.
   *
   * @param callback the callback
   */
  public void connect(IOCallback callback) {
    if (setAndConnect(null, callback) == false) {
      if (callback == null)
        throw new RuntimeException("callback may not be null.");
      else if (this.url == null)
        throw new RuntimeException(
          "connect(IOCallback) can only be invoked after SocketIO(String) or SocketIO(URL)");
    }
  }

  /**
   * Sets url and callback and initiates connecting if both are present
   *
   * @param url      the url
   * @param callback the callback
   *
   * @return true if connecting has been initiated, false if not
   */
  private boolean setAndConnect(URL url, IOCallback callback) {
    if ((this.url != null && url != null)
      || (this.callback != null && callback != null))
      return false;
    if (url != null) {
      this.url = url;
    }
    if (callback != null) {
      this.callback = callback;
    }
    if (this.callback != null && this.url != null) {
      final String origin = this.url.getProtocol() + "://"
        + this.url.getAuthority();
      this.namespace = this.url.getPath();
      if (this.namespace.equals("/")) {
        this.namespace = "";
      }
      this.connection = IOConnection.register(origin, this);
      return true;
    }
    return false;
  }

  /**
   * Emits an event to the Socket.IO server. If the connection is not
   * established, the call will be buffered and sent as soon as it is
   * possible.
   *
   * @param event the event name
   * @param args  the arguments
   */
  public void emit(final String event, final Object... args) {
    this.connection.emit(this, event, null, args);
  }

  /**
   * Emits an event to the Socket.IO server. If the connection is not
   * established, the call will be buffered and sent as soon as it is
   * possible.
   *
   * @param event the event name
   * @param ack   an acknowledge implementation
   * @param args  the arguments
   */
  public void emit(final String event, IOAcknowledge ack,
                   final Object... args) {
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
   * @param ack  an acknowledge implementation
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
   * @param ack     an acknowledge implementation
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
   * Triggers the transport to reconnect.
   *
   * This had become useful on some android devices which do not shut down
   * tcp-connections when switching from HSDPA to Wifi
   */
  public void reconnect() {
    this.connection.reconnect();
  }

  /**
   * Returns, if a connection is established at the moment
   *
   * @return true if a connection is established, false if the transport is
   *         not connected or currently connecting
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
