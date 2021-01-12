package org.knowm.xchange.koinim;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.koinim.service.KoinimMarketDataService;

/** @author ahmetoz */
public class KoinimExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {

    this.marketDataService = new KoinimMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.koinim.com");
    exchangeSpecification.setHost("www.koinim.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Koinim");
    exchangeSpecification.setExchangeDescription(
        "Koinim is a Bitcoin exchange registered in Turkey.");
    return exchangeSpecification;
  }
}
