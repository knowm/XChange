/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.service.streaming;

/**
 * <p>
 * Interface to provide the following to {@link com.xeiam.xchange.Exchange}:
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
   */
  void connect();

  /**
   * <p>
   * Closes the connection to the upstream server for this instance.
   * </p>
   */
  void disconnect();

  /**
   * <p>
   * The consumer exchange event queue containing events as described in {@link ExchangeEventType}. Examples include:
   * </p>
   * <ul>
   * <li>Connect/disconnect events</li>
   * <li>Ticker events (with Ticker embedded)</li>
   * <li>LimitOrder events (with LimitOrder embedded)</li>
   * </ul>
   * 
   * @return A blocking queue
   */
  ExchangeEvent getNextEvent() throws InterruptedException;

  /**
   * <p>
   * Sends a msg over the socket.
   * </p>
   */
  void send(String msg);

}
