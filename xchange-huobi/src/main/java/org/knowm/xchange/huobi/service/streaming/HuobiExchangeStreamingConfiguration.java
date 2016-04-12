package org.knowm.xchange.huobi.service.streaming;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.streaming.ExchangeStreamingConfiguration;

public class HuobiExchangeStreamingConfiguration implements ExchangeStreamingConfiguration {

  private final CurrencyPair[] marketDataCurrencyPairs;

  public HuobiExchangeStreamingConfiguration() {
    marketDataCurrencyPairs = new CurrencyPair[] { CurrencyPair.BTC_CNY, CurrencyPair.LTC_CNY };
  }

  public HuobiExchangeStreamingConfiguration(CurrencyPair[] currencyPairs) {
    marketDataCurrencyPairs = currencyPairs;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaxReconnectAttempts() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getReconnectWaitTimeInMs() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getTimeoutInMs() {
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEncryptedChannel() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean keepAlive() {
    return false;
  }

  public CurrencyPair[] getMarketDataCurrencyPairs() {
    return marketDataCurrencyPairs;
  }

}
