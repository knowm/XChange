package com.xeiam.xchange.bitcoinium;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataService;

public class BitcoiniumExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoiniumMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcoinium.com:443");
    exchangeSpecification.setHost("bitcoinium.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Bitcoinium");
    exchangeSpecification.setExchangeDescription("Bitcoinium Web Service provides compact and filtered data from various bitcoin exchanges");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
