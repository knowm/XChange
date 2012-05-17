/*
 * socket.io-java-client WebsocketTransport.java
 *
 * Copyright (c) 2011, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Pattern;

import com.xeiam.xchange.streaming.websocket.HandshakeData;
import com.xeiam.xchange.streaming.websocket.WebSocketClient;

/**
 * The Class WebSocketTransport.
 */
class WebSocketTransport extends WebSocketClient implements IOTransport {

  /**
   * Pattern used to replace http:// by ws:// respectively https:// by wss://
   */
  private final static Pattern PATTERN_HTTP = Pattern.compile("^http");

  /**
   * The String to identify this Transport
   */
  public static final String TRANSPORT_NAME = "websocket";

  /**
   * The IOConnection of this transport.
   */
  private IOConnection connection;

  /**
   * Creates a new Transport for the given url an {@link IOConnection}.
   * 
   * @param url the url
   * @param connection the connection
   * @return the iO transport
   */
  public static IOTransport create(String urlString, IOConnection connection) {
    URI uri = URI.create(PATTERN_HTTP.matcher(urlString).replaceFirst("ws") + IOConnection.SOCKET_IO_1 + TRANSPORT_NAME + "/" + connection.getSessionId());

    return new WebSocketTransport(uri, connection);
  }

  /**
   * Instantiates a new WebSocket transport.
   * 
   * @param uri the uri
   * @param connection the connection
   */
  public WebSocketTransport(URI uri, IOConnection connection) {
    super(uri);
    this.connection = connection;
  }

  @Override
  public void disconnect() {
    try {
      close();
    } catch (Exception e) {
      connection.transportError(e);
    }
  }

  @Override
  public boolean canSendBulk() {
    return false;
  }

  @Override
  public void sendBulk(String[] texts) throws IOException {
    throw new RuntimeException("Cannot send Bulk!");
  }

  @Override
  public void invalidate() {
    connection = null;
  }

  @Override
  public void onMessage(String message) {
    if (connection != null) {
      connection.transportMessage(message);
    }
  }

  @Override
  public void onOpen(HandshakeData handshakeData) {
    if (connection != null) {
      connection.transportConnected();
    }
  }

  @Override
  public void onClose(int code, String reason, boolean remote) {
    if (connection != null) {
      connection.transportDisconnected();
    }
  }

  @Override
  public void onError(Exception ex) {
    connection.error(new SocketIOException(ex.getMessage(), ex));
  }

  @Override
  public String getName() {
    return TRANSPORT_NAME;
  }
}
