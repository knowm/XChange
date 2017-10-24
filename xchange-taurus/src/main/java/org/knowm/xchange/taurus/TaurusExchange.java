package org.knowm.xchange.taurus;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.taurus.service.TaurusAccountService;
import org.knowm.xchange.taurus.service.TaurusMarketDataService;
import org.knowm.xchange.taurus.service.TaurusTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class TaurusExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new TaurusMarketDataService(this);
    this.tradeService = new TaurusTradeService(this);
    this.accountService = new TaurusAccountService(this);
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
