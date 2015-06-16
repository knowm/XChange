package com.xeiam.xchange.bitcurex;

import com.xeiam.xchange.bitcurex.service.polling.BitcurexAccountService;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTime1000NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

public class BitcurexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcurexMarketDataService(this);
    this.pollingAccountService = new BitcurexAccountService(this);
    this.pollingTradeService = new BitcurexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcurex.com");
    exchangeSpecification.setPort(8080);
    exchangeSpecification.setHost("bitcurex.com");
    exchangeSpecification.setExchangeName("Bitcurex");
    exchangeSpecification.setExchangeDescription("Bitcurex is a polish Bitcoin exchange");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
