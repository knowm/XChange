package org.knowm.xchange.bter;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bter.service.polling.BTERPollingAccountService;
import org.knowm.xchange.bter.service.polling.BTERPollingMarketDataService;
import org.knowm.xchange.bter.service.polling.BTERPollingTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BTERExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
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
