/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.mtgox.v2.service.trade.streaming;

import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * <p>
 * Value object to provide the following
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange streaming API</li>
 * </ul>
 */
public class MtGoxStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final String tradeableIdentifier;
  private final String currencyCode;

  /**
   * Constructor
   * 
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param tradeableIdentifier
   * @param currencyCode
   */
  public MtGoxStreamingConfiguration(int maxReconnectAttempts, int reconnectWaitTimeInMs, String tradeableIdentifier, String currencyCode) {

    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.tradeableIdentifier = tradeableIdentifier;
    this.currencyCode = currencyCode;
  }

  public String getTradeableIdentifier() {

    return tradeableIdentifier;
  }

  public String getCurrencyCode() {

    return currencyCode;
  }

  @Override
  public int getMaxReconnectAttempts() {

    return maxReconnectAttempts;
  }

  @Override
  public int getRecconectWaitTimeInMs() {

    return reconnectWaitTimeInMs;
  }

}
