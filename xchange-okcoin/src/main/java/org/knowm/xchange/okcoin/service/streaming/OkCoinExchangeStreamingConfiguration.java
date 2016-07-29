package org.knowm.xchange.okcoin.service.streaming;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

public class OkCoinExchangeStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final CurrencyPair[] marketDataCurrencyPairs;

  public OkCoinExchangeStreamingConfiguration() {
    marketDataCurrencyPairs = new CurrencyPair[] { CurrencyPair.BTC_CNY };
  }

  public OkCoinExchangeStreamingConfiguration(CurrencyPair[] marketDataCurrencyPairs) {
    this.marketDataCurrencyPairs = marketDataCurrencyPairs;
  }

  @Override
  public int getMaxReconnectAttempts() {
    return 0;
  }

  @Override
  public int getReconnectWaitTimeInMs() {
    return 0;
  }

  @Override
  public int getTimeoutInMs() {
    return 0;
  }

  @Override
  public boolean isEncryptedChannel() {
    return false;
  }

  @Override
  public boolean keepAlive() {
    return false;
  }

  public CurrencyPair[] getMarketDataCurrencyPairs() {
    return marketDataCurrencyPairs;
  }
}
