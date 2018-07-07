package org.knowm.xchange.coingi;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coingi.service.CoingiAccountService;
import org.knowm.xchange.coingi.service.CoingiMarketDataService;
import org.knowm.xchange.coingi.service.CoingiTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoingiExchange extends BaseExchange implements Exchange {
  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CoingiMarketDataService(this);
    this.tradeService = new CoingiTradeService(this);
    this.accountService = new CoingiAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.coingi.com");
    exchangeSpecification.setHost("api.coingi.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coingi");
    exchangeSpecification.setExchangeDescription("Coingi is a cryptocurrency exchange.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
