package org.knowm.xchange.chbtc;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.chbtc.service.ChbtcMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class ChbtcExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new ChbtcMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("http://api.chbtc.com");
    exchangeSpecification.setHost("chbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CHBTC");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException();
  }
}
