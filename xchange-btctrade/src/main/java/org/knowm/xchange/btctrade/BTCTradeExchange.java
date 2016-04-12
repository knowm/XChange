package org.knowm.xchange.btctrade;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.service.polling.BTCTradeAccountService;
import org.knowm.xchange.btctrade.service.polling.BTCTradeMarketDataService;
import org.knowm.xchange.btctrade.service.polling.BTCTradeTradeService;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BTCTradeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
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
