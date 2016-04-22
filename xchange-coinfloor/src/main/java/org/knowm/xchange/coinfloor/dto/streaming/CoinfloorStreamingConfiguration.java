package org.knowm.xchange.coinfloor.dto.streaming;

import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

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
   * @param keepAlive if true, will ping server every 15s to keep connection alive. If false, server will likely terminate connection after 60s of
   *        inactivity.
   * @param isEncryptedChannel if true, use WSS:// (SSL link)
   * @param authenticateOnConnect setting this flag to true will cause the CoinfloorStreamingExchangeService to authenticate immediately upon
   *        connection. If false, authenticated methods will not be availiable unless authenicate() is called. Relies on userID/Cookie/Password from
   *        exchangeSpecification
   */
  public CoinfloorStreamingConfiguration(int maxReconnectAttempts, int reconnectWaitTimeInMs, int timeoutInMs, boolean isEncryptedChannel,
      boolean keepAlive, boolean authenticateOnConnect) {

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
