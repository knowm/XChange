package org.knowm.xchange.service.streaming;

import org.java_websocket.WebSocket.READYSTATE;

/**
 * <p>
 * Interface to provide the following to {@link org.knowm.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data using asynchronous streaming data feeds</li>
 * </ul>
 */
public interface StreamingExchangeService {

  /**
   * <p>
   * Opens the connection to the upstream server for this instance.
   * </p>
   * 
   * @throws Exception
   */
  void connect() throws Exception;

  /**
   * <p>
   * Closes the connection to the upstream server for this instance.
   * </p>
   */
  void disconnect();

  /**
   * <p>
   * Returns next event in consumer event queue, then removes it.
   * </p>
   * 
   * @return An ExchangeEvent
   */
  ExchangeEvent getNextEvent() throws InterruptedException;

  /**
   * <p>
   * Returns number of events in consumer event queue.
   * </p>
   * 
   * @return An int
   */
  int countEventsAvailable();

  /**
   * <p>
   * Sends a msg over the socket.
   * </p>
   */
  void send(String msg);

  /**
   * Returns current state of websocket connection. Will return one of these values: NOT_YET_CONNECTED, CONNECTING, OPEN, CLOSING, CLOSED
   * 
   * @return enum of type READYSTATE
   */
  READYSTATE getWebSocketStatus();
}
