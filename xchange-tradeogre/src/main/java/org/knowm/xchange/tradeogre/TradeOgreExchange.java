package org.knowm.xchange.tradeogre;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.tradeogre.service.TradeOgreAccountService;
import org.knowm.xchange.tradeogre.service.TradeOgreMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class TradeOgreExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    marketDataService = new TradeOgreMarketDataService(this);
    accountService = new TradeOgreAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://tradeogre.com/api/v1");
    exchangeSpecification.setHost("tradeogre.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("TradeOgre");
    exchangeSpecification.setExchangeDescription("TradeOgre is a cryptocurrency exchange.");
    return exchangeSpecification;
  }
}
