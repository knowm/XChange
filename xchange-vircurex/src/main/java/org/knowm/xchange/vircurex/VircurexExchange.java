package org.knowm.xchange.vircurex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTime250NonceFactory;
import org.knowm.xchange.vircurex.service.VircurexAccountService;
import org.knowm.xchange.vircurex.service.VircurexMarketDataService;
import org.knowm.xchange.vircurex.service.VircurexTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class VircurexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime250NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new VircurexMarketDataService(this);
    this.accountService = new VircurexAccountService(this);
    this.tradeService = new VircurexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vircurex.com");
    exchangeSpecification.setExchangeName("Vircurex");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
