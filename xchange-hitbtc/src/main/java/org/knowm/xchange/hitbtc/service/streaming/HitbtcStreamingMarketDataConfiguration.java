package org.knowm.xchange.hitbtc.service.streaming;

import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

public class HitbtcStreamingMarketDataConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final int timeoutInMs;

  /**
   * Constructor
   *
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param timeoutInMs
   */
  public HitbtcStreamingMarketDataConfiguration(int maxReconnectAttempts, int reconnectWaitTimeInMs, int timeoutInMs) {

    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.timeoutInMs = timeoutInMs;
  }

  public HitbtcStreamingMarketDataConfiguration() {

    maxReconnectAttempts = 30; // 67 min
    reconnectWaitTimeInMs = 135000; // 2:15
    timeoutInMs = 120000; // 2:00
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

    return false;
  }

  @Override
  public boolean keepAlive() {
    return true;
  }
}
