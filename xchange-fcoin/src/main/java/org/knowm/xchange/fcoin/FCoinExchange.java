package org.knowm.xchange.fcoin;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.fcoin.service.FCoinTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class FCoinExchange extends BaseExchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.tradeService = new FCoinTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.fcoin.com");
    exchangeSpecification.setHost("api.fcoin.com");
    exchangeSpecification.setExchangeName("FCoin");
    exchangeSpecification.setMetaDataJsonFileOverride(null);
    exchangeSpecification.setExchangeDescription(
        "FCoin is a globally oriented crypto-currency trading platform.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    // This exchange doesn't use a nonce for authentication
    return null;
  }
}
