package com.xeiam.xchange.bleutrade;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeAccountService;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeMarketDataService;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

public class BleutradeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BleutradeMarketDataService(this);
    this.pollingAccountService = new BleutradeAccountService(this);
    this.pollingTradeService = new BleutradeTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bleutrade.com/api/");
    exchangeSpecification.setHost("bleutrade.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bleutrade");
    exchangeSpecification.setExchangeDescription("Bleutrade is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
