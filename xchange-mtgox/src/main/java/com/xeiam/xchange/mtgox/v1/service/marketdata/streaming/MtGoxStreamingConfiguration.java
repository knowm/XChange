package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming;

import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;

/**
 * <p>
 * Value object to provide the following
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange streaming API</li>
 * </ul>
 * <p>
 * 
 * @deprecated Use V2! This will be removed in 1.8.0+
 */
@Deprecated
public class MtGoxStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final int maxReconnectAttempts;
  private final int reconnectWaitTimeInMs;
  private final String tradeableIdentifier;
  private final String currencyCode;
  private final boolean encryptedChannel;

  /**
   * Constructor
   * 
   * @param maxReconnectAttempts
   * @param reconnectWaitTimeInMs
   * @param tradeableIdentifier
   * @param currencyCode
   */
  public MtGoxStreamingConfiguration(int maxReconnectAttempts, int reconnectWaitTimeInMs, String tradeableIdentifier, String currencyCode, boolean encryptedChannel) {

    this.maxReconnectAttempts = maxReconnectAttempts;
    this.reconnectWaitTimeInMs = reconnectWaitTimeInMs;
    this.tradeableIdentifier = tradeableIdentifier;
    this.currencyCode = currencyCode;
    this.encryptedChannel = encryptedChannel;
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
  public int getReconnectWaitTimeInMs() {

    return reconnectWaitTimeInMs;
  }

  @Override
  public int getTimeoutInMs() {

    return 0;
  }

  @Override
  public boolean isEncryptedChannel() {

    return encryptedChannel;
  }

}
