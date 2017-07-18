package org.knowm.xchange.jubi;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.jubi.service.JubiAccountService;
import org.knowm.xchange.jubi.service.JubiMarketDataService;
import org.knowm.xchange.jubi.service.JubiTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class JubiExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new JubiMarketDataService(this);
    this.accountService = new JubiAccountService(this);
    this.tradeService = new JubiTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.jubi.com");
    exchangeSpecification.setHost("www.jubi.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Jubi");
    exchangeSpecification.setExchangeDescription("Jubi is a Bitcoin exchange");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
