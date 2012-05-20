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
