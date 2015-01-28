package com.xeiam.xchange.coinbase;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountService;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataService;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

/**
 * @author jamespedwards42
 */
public class CoinbaseExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CoinbaseMarketDataService(this);
    this.pollingAccountService = new CoinbaseAccountService(this);
    this.pollingTradeService = new CoinbaseTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://coinbase.com");
    exchangeSpecification.setHost("coinbase.com");
    exchangeSpecification.setExchangeName("Coinbase");
    exchangeSpecification
    .setExchangeDescription("Founded in June of 2012, Coinbase is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

}
