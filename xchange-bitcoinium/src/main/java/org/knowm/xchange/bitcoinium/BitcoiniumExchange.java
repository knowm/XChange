package org.knowm.xchange.bitcoinium;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinium.service.BitcoiniumMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoiniumExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new BitcoiniumMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcoinium.com:443");
    exchangeSpecification.setHost("bitcoinium.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Bitcoinium");
    exchangeSpecification.setExchangeDescription(
        "Bitcoinium Web Service provides compact and filtered data from various bitcoin exchanges");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
