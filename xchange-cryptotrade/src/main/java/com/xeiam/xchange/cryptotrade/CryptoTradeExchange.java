package com.xeiam.xchange.cryptotrade;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeAccountService;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeMarketDataService;
import com.xeiam.xchange.cryptotrade.service.polling.CryptoTradeTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

public class CryptoTradeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CryptoTradeMarketDataService(this);
    this.pollingAccountService = new CryptoTradeAccountService(this);
    this.pollingTradeService = new CryptoTradeTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://crypto-trade.com");
    exchangeSpecification.setHost("crypto-trade.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Crypto Trade");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
