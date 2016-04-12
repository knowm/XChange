package org.knowm.xchange.btc38;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btc38.service.polling.Btc38MarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * Created by Yingzhe on 12/17/2014.
 */
public class Btc38Exchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  /**
   * Default constructor for Btc38Exchange
   */
  public Btc38Exchange() {

  }

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new Btc38MarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("http://api.btc38.com");
    exchangeSpecification.setHost("api.btc38.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Btc38");
    exchangeSpecification.setExchangeDescription("Btc38 is a Chinese Bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
