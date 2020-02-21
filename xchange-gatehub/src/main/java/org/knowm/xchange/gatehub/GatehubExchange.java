package org.knowm.xchange.gatehub;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gatehub.service.polling.GatehubAccountService;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Sumedha
 */
public class GatehubExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.pollingAccountService = new GatehubAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.gatehub.net");
    exchangeSpecification.setHost("api.gatecoin.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("GateHub");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException();
  }

}
