package com.xeiam.xchange.jubi;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.jubi.service.polling.JubiMarketDataService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Yingzhe on 3/16/2015.
 */
public class JubiExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  public JubiExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new JubiMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("http://www.jubi.com/api");
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
