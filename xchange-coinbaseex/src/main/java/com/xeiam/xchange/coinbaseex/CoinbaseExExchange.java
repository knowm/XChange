package com.xeiam.xchange.coinbaseex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbaseex.service.polling.CoinbaseExAccountService;
import com.xeiam.xchange.coinbaseex.service.polling.CoinbaseExMarketDataService;
import com.xeiam.xchange.coinbaseex.service.polling.CoinbaseExTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

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
