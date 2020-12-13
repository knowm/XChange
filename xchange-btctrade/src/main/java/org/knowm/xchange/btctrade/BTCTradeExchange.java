package org.knowm.xchange.btctrade;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.service.BTCTradeAccountService;
import org.knowm.xchange.btctrade.service.BTCTradeMarketDataService;
import org.knowm.xchange.btctrade.service.BTCTradeTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCTradeExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.NANOSECONDS);

  @Override
  protected void initServices() {
    this.marketDataService = new BTCTradeMarketDataService(this);
    if (exchangeSpecification.getApiKey() != null) {
      this.accountService = new BTCTradeAccountService(this);
      this.tradeService = new BTCTradeTradeService(this);
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
