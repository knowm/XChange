/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.xeiam.xchange.service.streaming;

/**
 * <p>
 * Signature interface to provide the following to exchange services:
 * </p>
 * <ul>
 * <li>A common entry point for the application of configuration data</li>
 * </ul>
 * <p>
 * Often it is necessary for additional configuration to be applied to the exchange data feeds after the initial creation with ExchangeSpecification has completed. This is the mechanism to achieve
 * this.
 * </p>
 */
public interface ExchangeStreamingConfiguration {

  /**
   * What are the maximum number of reconnect attempts?
   * 
   * @return
   */
  public int getMaxReconnectAttempts();

  /**
   * Before attempting reconnect, how much of a delay?
   * 
   * @return
   */
  public int getReconnectWaitTimeInMs();

  /**
   * How much time should elapse before the connection is considered dead and a reconnect attempt should be made?
   * 
   * @return
   */
  public int getTimeoutInMs();

  /**
   * should it use an encrypted channel or not? (ws vs. wss protocol)
   * 
   * @return
   */
  public boolean isEncryptedChannel();

  /**
   * should it keep the socket alive? (send ping every 15s)
   * 
   * @return
   */
  public boolean keepAlive();

}
