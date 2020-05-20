package org.knowm.xchange.enigma;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.service.EnigmaAccountService;
import org.knowm.xchange.enigma.service.EnigmaMarketDataService;
import org.knowm.xchange.enigma.service.EnigmaTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class EnigmaExchange extends BaseExchange {

  // private static final String SSL_URI = "https://api-test.enigma-securities.io/";
  // private static final String HOST = "api-test.enigma-securities.io";

  private static final String SSL_URI = "https://api.enigma-securities.io/";
  private static final String HOST = "api.enigma-securities.io";

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new EnigmaMarketDataService(this);
    this.tradeService = new EnigmaTradeService(this);
    this.accountService = new EnigmaAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri(SSL_URI);
    exchangeSpecification.setHost(HOST);
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Enigma");
    exchangeSpecification.setExchangeDescription("Enigma Securities REST API integration");
    exchangeSpecification.setShouldLoadRemoteMetaData(false);

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
