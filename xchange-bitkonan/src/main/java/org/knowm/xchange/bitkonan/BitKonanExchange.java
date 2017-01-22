package org.knowm.xchange.bitkonan;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitkonan.service.BitKonanMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class BitKonanExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new BitKonanMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitkonan.com");
    exchangeSpecification.setHost("bitkonan.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitKonan");
    exchangeSpecification.setExchangeDescription("BitKonan is a Bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
