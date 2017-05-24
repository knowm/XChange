package org.knowm.xchange.btc38;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btc38.service.Btc38MarketDataService;
import org.knowm.xchange.btc38.service.Btc38MarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.HashMap;

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
    HashMap<String, CurrencyPair> pairs = ((Btc38MarketDataServiceRaw) marketDataService).getCurrencyPairMap();
    exchangeMetaData = Btc38Adapters.adaptToExchangeMetaData(pairs.values());
  }


}
