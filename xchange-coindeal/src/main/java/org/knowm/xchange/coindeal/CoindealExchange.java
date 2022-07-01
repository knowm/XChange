package org.knowm.xchange.coindeal;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coindeal.service.CoindealAccountService;
import org.knowm.xchange.coindeal.service.CoindealMarketDataService;
import org.knowm.xchange.coindeal.service.CoindealTradeService;
import org.knowm.xchange.exceptions.ExchangeException;

public class CoindealExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new CoindealMarketDataService(this);
    this.accountService = new CoindealAccountService(this);
    this.tradeService = new CoindealTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://apigateway.coindeal.com");
    exchangeSpecification.setHost("www.coindeal.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coindeal");
    exchangeSpecification.setExchangeDescription("Coindeal is a exchange based in Malta.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    super.remoteInit();
  }
}
