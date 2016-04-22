package org.knowm.xchange.cryptonit.v2;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptonit.v2.service.polling.CryptonitMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class CryptonitExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CryptonitMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://cryptonit.net");
    exchangeSpecification.setHost("cryptonit.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptonit");
    exchangeSpecification
        .setExchangeDescription("Cryptonit is a cryptocurrency market owned and operated by UK based company Cryptonit Solutions Ltd.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
