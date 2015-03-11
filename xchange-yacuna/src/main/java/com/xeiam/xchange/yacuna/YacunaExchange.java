package com.xeiam.xchange.yacuna;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;
import com.xeiam.xchange.yacuna.service.polling.YacunaMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  public YacunaExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
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
