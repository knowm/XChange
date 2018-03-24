package org.knowm.xchange.cryptocompare;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptocompare.service.CryptocompareMarketDataService;

import lombok.ToString;
import si.mazi.rescu.SynchronizedValueFactory;

@ToString
public class CryptocompareExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CryptocompareMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://min-api.cryptocompare.com");
    exchangeSpecification.setHost("min-api.cryptocompare.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptocompare");
    exchangeSpecification.setExchangeDescription("Cryptocompare provides history data from various other bitcoin exchanges");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
