package org.knowm.xchange.dase;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dase.service.DaseAccountService;
import org.knowm.xchange.dase.service.DaseMarketDataService;
import org.knowm.xchange.dase.service.DaseTradeService;

public class DaseExchange extends BaseExchange {

  @Override
  protected void initServices() {
    this.marketDataService = new DaseMarketDataService(this);
    this.accountService = new DaseAccountService(this);
    this.tradeService = new DaseTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://api.dase.com");
    spec.setHost("api.dase.com");
    spec.setExchangeName("Dase");
    spec.setExchangeDescription("Dase via XChange");
    return spec;
  }
}
