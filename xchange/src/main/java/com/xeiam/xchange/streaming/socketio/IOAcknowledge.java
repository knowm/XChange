/*
 * socket.io-java-client IOAcknowledge.java
 *
 * Copyright (c) 2012, Enno Boland
 * PROJECT DESCRIPTION
 * 
 * See LICENSE file for more information
 */
package com.xeiam.xchange.streaming.socketio;

/**
 * The Interface IOAcknowledge.
 */
public interface IOAcknowledge {

  /**
   * Acknowledges a socket.io message.
   *
   * @param args may be all types
   */
  void ack(Object... args);
}
