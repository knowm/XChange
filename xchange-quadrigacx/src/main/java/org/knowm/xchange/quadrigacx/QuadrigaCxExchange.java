package org.knowm.xchange.quadrigacx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.quadrigacx.service.polling.QuadrigaCxAccountService;
import org.knowm.xchange.quadrigacx.service.polling.QuadrigaCxMarketDataService;
import org.knowm.xchange.quadrigacx.service.polling.QuadrigaCxTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class QuadrigaCxExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new QuadrigaCxMarketDataService(this);
    this.pollingAccountService = new QuadrigaCxAccountService(this);
    this.pollingTradeService = new QuadrigaCxTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.quadrigacx.com");
    exchangeSpecification.setHost("quadrigacx.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("QuadrigaCx");
    exchangeSpecification.setExchangeDescription("QuadrigaCX is a Canadian Cryptocurrency exchange platform, with offices in Vancouver, BC.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
