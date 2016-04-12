package org.knowm.xchange.empoex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.empoex.service.polling.EmpoExAccountService;
import org.knowm.xchange.empoex.service.polling.EmpoExMarketDataService;
import org.knowm.xchange.empoex.service.polling.EmpoExTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class EmpoExExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new EmpoExMarketDataService(this);
    this.pollingAccountService = new EmpoExAccountService(this);
    this.pollingTradeService = new EmpoExTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.empoex.com/");
    exchangeSpecification.setHost("api.empoex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("EmpoEX");
    exchangeSpecification.setExchangeDescription("EmpoEX is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // This exchange doesn't use nones for authentication
    return null;
  }
}
