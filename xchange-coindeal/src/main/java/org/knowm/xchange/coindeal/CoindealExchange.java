package org.knowm.xchange.coindeal;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindeal.service.CoindealMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoindealExchange extends BaseExchange implements Exchange {
  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CoindealMarketDataService(this);
    this.accountService = accountService;
    this.tradeService = tradeService;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://apigateway.coindeal.com");
    exchangeSpecification.setHost("www.coindeal.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coindeal");
    exchangeSpecification.setExchangeDescription("Coindeal is a exchange based in Cyprus.");
    return exchangeSpecification;
  }
}
