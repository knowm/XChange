package org.knowm.xchange.bl3p;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bl3p.service.Bl3pAccountService;
import org.knowm.xchange.bl3p.service.Bl3pMarketDataService;
import org.knowm.xchange.bl3p.service.Bl3pTradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class Bl3pExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new Bl3pMarketDataService(this);
    this.accountService = new Bl3pAccountService(this);
    this.tradeService = new Bl3pTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bl3p.eu/");
    exchangeSpecification.setHost("api.bl3p.eu");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bl3p");
    exchangeSpecification.setExchangeDescription("Bl3p is a Dutch BTC/LTC exchange");

    AuthUtils.setApiAndSecretKey(exchangeSpecification, "bl3p");

    return exchangeSpecification;
  }
}
