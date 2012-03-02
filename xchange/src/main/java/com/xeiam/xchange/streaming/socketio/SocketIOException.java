/*
 * socket.io-java-client SocketIOException.java
 *
 * Copyright (c) 2011, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

/**
 * The Class SocketIOException.
 */
public class SocketIOException extends Exception {

  /**
   * The Constant serialVersionUID.
   */
  private static final long serialVersionUID = 4965561569568761814L;

  /**
   * Instantiates a new SocketIOException.
   *
   * @param message the message
   */
  public SocketIOException(String message) {
    super(message);
  }

  /**
   * Instantiates a new SocketIOException.
   *
   * @param message The message
   * @param cause   The cause
   */
  public SocketIOException(String message, Exception cause) {
    super(message, cause);
  }
}
