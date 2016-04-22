package org.knowm.xchange.coinbaseex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbaseex.service.polling.CoinbaseExAccountService;
import org.knowm.xchange.coinbaseex.service.polling.CoinbaseExMarketDataService;
import org.knowm.xchange.coinbaseex.service.polling.CoinbaseExTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class CoinbaseExExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new CoinbaseExMarketDataService(this);
    this.pollingAccountService = new CoinbaseExAccountService(this);
    this.pollingTradeService = new CoinbaseExTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.exchange.coinbase.com");
    exchangeSpecification.setHost("api.exchange.coinbase.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("CoinbaseEx");
    exchangeSpecification.setExchangeDescription("Coinbase Exchange is a Bitcoin exchange recently launched in January 2015");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
