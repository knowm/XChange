package org.knowm.xchange.bithumb;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bithumb.service.BithumbAccountService;
import org.knowm.xchange.bithumb.service.BithumbMarketDataService;
import org.knowm.xchange.bithumb.service.BithumbTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BithumbExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new BithumbMarketDataService(this);
    this.tradeService = new BithumbTradeService(this);
    this.accountService = new BithumbAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bithumb.com");
    exchangeSpecification.setHost("api.bithumb.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bithumb");
    exchangeSpecification.setExchangeDescription(
        "Bithumb is a Bitcoin exchange registered in South Korea.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
