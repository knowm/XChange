package org.knowm.xchange.enigma;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.service.EnigmaAccountService;
import org.knowm.xchange.enigma.service.EnigmaMarketDataService;
import org.knowm.xchange.enigma.service.EnigmaTradeService;

public class EnigmaExchange extends BaseExchange {

  // private static final String SSL_URI = "https://api-test.enigma-securities.io/";
  // private static final String HOST = "api-test.enigma-securities.io";

  private static final String SSL_URI = "https://api.enigma-securities.io/";
  private static final String HOST = "api.enigma-securities.io";

  @Override
  protected void initServices() {
    this.marketDataService = new EnigmaMarketDataService(this);
    this.tradeService = new EnigmaTradeService(this);
    this.accountService = new EnigmaAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri(SSL_URI);
    exchangeSpecification.setHost(HOST);
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Enigma");
    exchangeSpecification.setExchangeDescription("Enigma Securities REST API integration");
    exchangeSpecification.setShouldLoadRemoteMetaData(false);

    return exchangeSpecification;
  }
}
