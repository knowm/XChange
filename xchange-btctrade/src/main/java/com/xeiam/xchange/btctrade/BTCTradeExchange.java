package com.xeiam.xchange.btctrade;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeAccountService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataService;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeTradeService;
import com.xeiam.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;

public class BTCTradeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTCTradeMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null) {
      this.pollingAccountService = new BTCTradeAccountService(this);
      this.pollingTradeService = new BTCTradeTradeService(this);
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(getClass());
    exchangeSpecification.setSslUri("https://www.btctrade.com/api");
    exchangeSpecification.setHost("www.btctrade.com");
    exchangeSpecification.setExchangeName("BTCTrade");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
