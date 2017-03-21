package org.knowm.xchange.btc38;

import java.io.IOException;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btc38.service.Btc38MarketDataService;
import org.knowm.xchange.exceptions.ExchangeException;
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
    this.marketDataService = new Btc38MarketDataService(this);
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

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    // TODO Implement this.
    //    HashMap<String, CurrencyPair> pairs = ((Btc38MarketDataServiceRaw) marketDataService). getCurrencyPairMap()();
    // TODO take all the info and create a `ExchangeMetaData` object via a new method in `*Adapters` class
    //    exchangeMetaData = *Adapters.adaptToExchangeMetaData(blah, blah);
    super.remoteInit();
  }
}
