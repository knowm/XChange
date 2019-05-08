package org.knowm.xchange.enigma;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.service.EnigmaAccountService;
import org.knowm.xchange.enigma.service.EnigmaMarketDataService;
import org.knowm.xchange.enigma.service.EnigmaTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class EnigmaExchange extends BaseExchange {

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
    exchangeSpecification.setSslUri("https://beta.enigma-securities.io/");
    exchangeSpecification.setHost("beta.enigma-securities.io");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Enigma");
    exchangeSpecification.setExchangeDescription("Enigma is a Bitcoin exchange");
    exchangeSpecification.setShouldLoadRemoteMetaData(false);
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
