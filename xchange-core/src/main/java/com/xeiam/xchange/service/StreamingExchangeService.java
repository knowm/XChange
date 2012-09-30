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

/**
 * <p>
 * Interface to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>Standard methods available to explore the market data using asynchronous streaming data feeds</li>
 * </ul>
 * <p>
 * Note that the asynchronous nature of this service strongly encourages a thread based implementation.
 * </p>
 */
public interface StreamingExchangeService {

  /**
   * <p>
   * Start the service and provide a suitable runnable event listener to receive events. This will be configured by the service.
   * </p>
   */
  void connect(String url, RunnableExchangeEventListener runnableMarketDataListener);

  /**
   * <p>
   * Closes the connection to the upstream server for this instance.
   * </p>
   */
  void disconnect();

  /**
   * <p>
   * sends a message to the upstream server for this instance.
   * </p>
   */
  void send(String message);

  /**
   * @return The runnable market data event producer
   */
  RunnableExchangeEventProducer getRunnableMarketDataEventProducer();

  void setRunnableMarketDataEventProducer(RunnableExchangeEventProducer runnableMarketDataEventProducer);
}
