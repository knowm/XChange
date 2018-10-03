package org.knowm.xchange.btcturk;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author semihunaldi */
public class BTCTurkExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new BTCTurkMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.btcturk.com");
    exchangeSpecification.setHost("www.btcturk.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BTCTurk");
    exchangeSpecification.setExchangeDescription(
        "BTCTurk is a Bitcoin & Etherium exchange registered in Turkey.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
