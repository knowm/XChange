package com.xeiam.xchange.taurus;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.taurus.service.polling.TaurusAccountService;
import com.xeiam.xchange.taurus.service.polling.TaurusMarketDataService;
import com.xeiam.xchange.taurus.service.polling.TaurusTradeService;
import com.xeiam.xchange.service.streaming.ExchangeStreamingConfiguration;
import com.xeiam.xchange.service.streaming.StreamingExchangeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

/**
 * @author Matija Mazi
 */
public class TaurusExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new TaurusMarketDataService(this);
    this.pollingTradeService = new TaurusTradeService(this);
    this.pollingAccountService = new TaurusAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.taurusexchange.com");
    exchangeSpecification.setHost("www.taurusexchange.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Taurus");
    exchangeSpecification.setExchangeDescription("Taurus Exchange");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
