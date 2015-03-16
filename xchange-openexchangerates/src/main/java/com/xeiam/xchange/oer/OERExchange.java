package com.xeiam.xchange.oer;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.oer.service.polling.OERMarketDataService;

public class OERExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new OERMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setHost("openexchangerates.org");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Open Exchange Rates");
    exchangeSpecification.setExchangeDescription("Open Exchange Rates is an exchange rate provider for a wide range of currencies.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
