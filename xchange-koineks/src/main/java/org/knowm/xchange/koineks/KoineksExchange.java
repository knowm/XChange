package org.knowm.xchange.koineks;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.koineks.service.KoineksMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author semihunaldi */
public class KoineksExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new KoineksMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://koineks.com");
    exchangeSpecification.setHost("www.koineks.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Koineks");
    exchangeSpecification.setExchangeDescription(
        "Koineks is BTC, ETH, LTC, DASH and DOGE exchange registered in Turkey.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
