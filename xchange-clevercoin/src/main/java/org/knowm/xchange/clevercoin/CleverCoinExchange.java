package org.knowm.xchange.clevercoin;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.clevercoin.service.polling.CleverCoinAccountService;
import org.knowm.xchange.clevercoin.service.polling.CleverCoinMarketDataService;
import org.knowm.xchange.clevercoin.service.polling.CleverCoinTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Karsten Nilsen
 */
public class CleverCoinExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CleverCoinMarketDataService(this);
    this.pollingTradeService = new CleverCoinTradeService(this);
    this.pollingAccountService = new CleverCoinAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.clevercoin.com");
    exchangeSpecification.setHost("api.clevercoin.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CleverCoin");
    exchangeSpecification.setExchangeDescription("CleverCoin is a Bitcoin exchange registred in The Netherlands with banking partner in France.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
