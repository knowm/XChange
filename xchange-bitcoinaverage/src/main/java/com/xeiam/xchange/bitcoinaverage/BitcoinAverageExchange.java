package com.xeiam.xchange.bitcoinaverage;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinaverage.service.polling.BitcoinAverageMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BitcoinAverage API</li>
 * </ul>
 */
public class BitcoinAverageExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BitcoinAverageExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoinAverageMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitcoinaverage.com");
    exchangeSpecification.setHost("bitcoinaverage.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitcoinAverage");
    exchangeSpecification.setExchangeDescription("BitcoinAverage provides a more accurate price of bitcoin using weighted average for multiple exchanges.");

    return exchangeSpecification;
  }
}
