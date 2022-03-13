package org.knowm.xchange.coincheck;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coincheck.service.CoincheckMarketDataService;

public class CoincheckExchange extends BaseExchange implements Exchange {
  @Override
  protected void initServices() {
    this.marketDataService = new CoincheckMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://coincheck.com");
    exchangeSpecification.setHost("www.coincheck.net");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Coincheck");
    exchangeSpecification.setExchangeDescription("Coincheck Exchange in Japan.");
    return exchangeSpecification;
  }
}
