package org.knowm.xchange.paymium;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.paymium.service.PaymiumMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class PaymiumExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://paymium.com/api/v1/data/eur");
    exchangeSpecification.setHost("paymium.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Paymium");
    exchangeSpecification.setExchangeDescription("Paymium is a Bitcoin exchange registered and maintained by a company based in Paris, France.");

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new PaymiumMarketDataService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
