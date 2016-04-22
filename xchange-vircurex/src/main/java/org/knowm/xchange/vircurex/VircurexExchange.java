package org.knowm.xchange.vircurex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTime250NonceFactory;
import org.knowm.xchange.vircurex.service.polling.VircurexAccountService;
import org.knowm.xchange.vircurex.service.polling.VircurexMarketDataService;
import org.knowm.xchange.vircurex.service.polling.VircurexTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class VircurexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime250NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new VircurexMarketDataService(this);
    this.pollingAccountService = new VircurexAccountService(this);
    this.pollingTradeService = new VircurexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vircurex.com");
    exchangeSpecification.setExchangeName("Vircurex");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
