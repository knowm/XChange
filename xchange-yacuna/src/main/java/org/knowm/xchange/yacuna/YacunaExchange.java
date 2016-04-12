package org.knowm.xchange.yacuna;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.knowm.xchange.yacuna.service.polling.YacunaMarketDataService;

import si.mazi.rescu.SynchronizedValueFactory;

public class YacunaExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  public YacunaExchange() {

  }

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new YacunaMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.yacuna.com/api");
    exchangeSpecification.setHost("www.yacuna.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Yacuna");
    exchangeSpecification.setExchangeDescription("Yacuna is a Bitcoin exchange");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
