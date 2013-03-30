package com.xeiam.xchange.mtgox.v1.service.marketdata.streaming;

import com.xeiam.xchange.service.ExchangeStreamingConfiguration;

/**
 * <p>
 * Value object to provide the following
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange streaming API</li>
 * </ul>
 */
public class MtGoxStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final String tradeableIdentifier;
  private final String currencyCode;

  /**
   * Constructor
   * 
   * @param tradeableIdentifier
   * @param currencyCode
   */
  public MtGoxStreamingConfiguration(String tradeableIdentifier, String currencyCode) {

    this.tradeableIdentifier = tradeableIdentifier;
    this.currencyCode = currencyCode;
  }

  public String getTradeableIdentifier() {

    return tradeableIdentifier;
  }

  public String getCurrencyCode() {

    return currencyCode;
  }

}
