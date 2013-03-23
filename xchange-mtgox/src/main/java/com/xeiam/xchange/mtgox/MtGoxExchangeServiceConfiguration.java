package com.xeiam.xchange.mtgox;

import com.xeiam.xchange.service.ExchangeServiceConfiguration;

/**
 * <p>
 * Value object to provide the following to {@link com.xeiam.xchange.mtgox.v1.service.marketdata.streaming.socketio.MtGoxStreamingMarketDataService}:
 * </p>
 * <ul>
 * <li>Access to streaming data configuration specific to MtGox exchange API</li>
 * </ul>
 */
public class MtGoxExchangeServiceConfiguration implements ExchangeServiceConfiguration {

  private final String tradeableIdentifier;

  private final String currencyCode;

  public enum Channel {

    ticker, trades, depth;
  }

  private final Channel channel;

  public MtGoxExchangeServiceConfiguration(String tradeableIdentifier, String currencyCode, Channel channel) {

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

  public Channel getChannel() {

    return channel;
  }

}
