package org.knowm.xchange.paribu;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.paribu.service.ParibuMarketDataService;

/**
 * @author semihunaldi
 */
public class ParibuExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {

    this.marketDataService = new ParibuMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://www.paribu.com");
    exchangeSpecification.setHost("www.paribu.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Paribu");
    exchangeSpecification.setExchangeDescription(
        "Paribu is a Bitcoin exchange registered in Turkey.");
    return exchangeSpecification;
  }
}
