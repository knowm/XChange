package com.xeiam.xchange.bter;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.service.polling.BTERPollingAccountService;
import com.xeiam.xchange.bter.service.polling.BTERPollingMarketDataService;
import com.xeiam.xchange.bter.service.polling.BTERPollingTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

public class BTERExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTERPollingMarketDataService(this);
    this.pollingAccountService = new BTERPollingAccountService(this);
    this.pollingTradeService = new BTERPollingTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.bter.com");
    exchangeSpecification.setHost("bter.com");
    exchangeSpecification.setExchangeName("BTER");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

}
