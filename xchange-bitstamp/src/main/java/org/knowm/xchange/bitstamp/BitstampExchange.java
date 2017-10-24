package org.knowm.xchange.bitstamp;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.service.BitstampAccountService;
import org.knowm.xchange.bitstamp.service.BitstampMarketDataService;
import org.knowm.xchange.bitstamp.service.BitstampTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi
 */
public class BitstampExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {

    this.marketDataService = new BitstampMarketDataService(this);
    this.tradeService = new BitstampTradeService(this);
    this.accountService = new BitstampAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitstamp.net");
    exchangeSpecification.setHost("www.bitstamp.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitstamp");
    exchangeSpecification.setExchangeDescription("Bitstamp is a Bitcoin exchange registered in Slovenia.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
