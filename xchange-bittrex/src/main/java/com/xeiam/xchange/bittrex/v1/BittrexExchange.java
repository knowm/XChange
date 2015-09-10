package com.xeiam.xchange.bittrex.v1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexAccountService;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexMarketDataService;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BittrexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BittrexMarketDataService(this);
    this.pollingAccountService = new BittrexAccountService(this);
    this.pollingTradeService = new BittrexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bittrex.com/api/");
    exchangeSpecification.setHost("bittrex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bittrex");
    exchangeSpecification.setExchangeDescription("Bittrex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}