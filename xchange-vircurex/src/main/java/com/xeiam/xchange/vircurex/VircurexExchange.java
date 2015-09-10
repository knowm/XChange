package com.xeiam.xchange.vircurex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.nonce.CurrentTime250NonceFactory;
import com.xeiam.xchange.vircurex.service.polling.VircurexAccountService;
import com.xeiam.xchange.vircurex.service.polling.VircurexMarketDataService;
import com.xeiam.xchange.vircurex.service.polling.VircurexTradeService;

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
