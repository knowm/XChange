package com.xeiam.xchange.bitkonan;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitkonan.service.polling.BitKonanMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class BitKonanExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BitKonanMarketDataService(this);
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
