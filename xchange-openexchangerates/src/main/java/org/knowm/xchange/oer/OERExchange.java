package org.knowm.xchange.oer;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.oer.service.OERMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

public class OERExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new OERMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setHost("openexchangerates.org");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Open Exchange Rates");
    exchangeSpecification.setExchangeDescription(
        "Open Exchange Rates is an exchange rate provider for a wide range of currencies.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
