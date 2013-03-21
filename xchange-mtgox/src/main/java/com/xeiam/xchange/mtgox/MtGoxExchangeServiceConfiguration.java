package com.xeiam.xchange.mtgox;

import com.xeiam.xchange.service.ExchangeServiceConfiguration;

/**
 * <p>
 * Value object to provide the following to {@link com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.socketio.MtGoxStreamingMarketDataService}:
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange API</li>
 * </ul>
 * 
 * @since 1.4.1  
 */
public class MtGoxExchangeServiceConfiguration implements ExchangeServiceConfiguration {

  private final String tradeableIdentifier;

  private final String currencyCode;

  private final String channel;

  public MtGoxExchangeServiceConfiguration(String tradeableIdentifier, String currencyCode, String channel) {

    this.tradeableIdentifier = tradeableIdentifier;
    this.currencyCode = currencyCode;
    this.channel = channel;
  }

  public String getTradeableIdentifier() {

    return tradeableIdentifier;
  }

  public String getCurrencyCode() {

    return currencyCode;
  }

  public String getChannel() {

    return channel;
  }

}
