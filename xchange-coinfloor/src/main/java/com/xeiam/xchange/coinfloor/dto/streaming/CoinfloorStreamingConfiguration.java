/**
 * The MIT License
 * Copyright (c) 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.coinfloor.dto.streaming;

import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * @author obsessiveOrange
 */
public class CoinfloorStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final int timeoutInMs;
  private final boolean isEncryptedChannel;
  private final boolean keepAlive;
  private final boolean authenticateOnConnect;

  /**
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param timeoutInMs
   * @param keepAlive if true, will ping server every 15s to keep connection alive. If false, server will likely terminate connection after 60s of inactivity.
   * @param isEncryptedChannel if true, use WSS:// (SSL link)
   * @param authenticateOnConnect setting this flag to true will cause the CoinfloorStreamingExchangeService to authenticate immediately upon connection. If false, authenticated methods will not be
   *          availiable unless authenicate() is called. Relies on userID/Cookie/Password from exchangeSpecification
   */
  public CoinfloorStreamingConfiguration(int maxReconnectAttempts, int reconnectWaitTimeInMs, int timeoutInMs, boolean isEncryptedChannel, boolean keepAlive, boolean authenticateOnConnect) {

    super();
    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.timeoutInMs = timeoutInMs;
    this.isEncryptedChannel = isEncryptedChannel;
    this.keepAlive = keepAlive;
    this.authenticateOnConnect = authenticateOnConnect;
  }

  @Override
  public int getMaxReconnectAttempts() {

    return maxReconnectAttempts;
  }

  @Override
  public int getReconnectWaitTimeInMs() {

    return reconnectWaitTimeInMs;
  }

  @Override
  public int getTimeoutInMs() {

    return timeoutInMs;
  }

  @Override
  public boolean isEncryptedChannel() {

    return isEncryptedChannel;
  }

  @Override
  public boolean keepAlive() {

    return keepAlive;
  }

  public boolean getauthenticateOnConnect() {

    return authenticateOnConnect;
  }

}
