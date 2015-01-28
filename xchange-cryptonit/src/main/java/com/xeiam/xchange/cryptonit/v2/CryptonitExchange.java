package com.xeiam.xchange.cryptonit.v2;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.service.polling.CryptonitMarketDataService;

public class CryptonitExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
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
