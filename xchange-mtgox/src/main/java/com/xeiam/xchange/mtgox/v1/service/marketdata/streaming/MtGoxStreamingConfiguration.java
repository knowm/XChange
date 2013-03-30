package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming;

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
  private final int recconectWaitTimeInMs;
  private final String tradeableIdentifier;
  private final String currencyCode;

  /**
   * Constructor
   * 
   * @param maxReconnectAttempts
   * @param recconectWaitTimeInMs
   * @param tradeableIdentifier
   * @param currencyCode
   */
  public MtGoxStreamingConfiguration(int maxReconnectAttempts, int recconectWaitTimeInMs, String tradeableIdentifier, String currencyCode) {

    this.maxReconnectAttempts = maxReconnectAttempts;
    this.recconectWaitTimeInMs = recconectWaitTimeInMs;
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

    return recconectWaitTimeInMs;
  }

}
