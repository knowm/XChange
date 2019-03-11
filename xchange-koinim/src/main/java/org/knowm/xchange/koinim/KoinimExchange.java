package org.knowm.xchange.koinim;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.koinim.service.KoinimMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author ahmetoz */
public class KoinimExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new KoinimMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.koinim.com");
    exchangeSpecification.setHost("www.koinim.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Koinim");
    exchangeSpecification.setExchangeDescription(
        "Koinim is a Bitcoin exchange registered in Turkey.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
