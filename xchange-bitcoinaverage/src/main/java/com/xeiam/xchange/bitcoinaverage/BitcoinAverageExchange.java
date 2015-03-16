package com.xeiam.xchange.bitcoinaverage;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinaverage.service.polling.BitcoinAverageMarketDataService;

public class BitcoinAverageExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BitcoinAverageMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitcoinaverage.com");
    exchangeSpecification.setHost("bitcoinaverage.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin Average");
    exchangeSpecification
        .setExchangeDescription("Bitcoin Average provides a more accurate price of bitcoin using weighted average for multiple exchanges.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
