package org.knowm.xchange.coinex;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinex.service.CoinexAccountService;

public class CoinexExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.accountService = new CoinexAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.coinex.com");
    exchangeSpecification.setHost("www.coinex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Coinex");
    exchangeSpecification.setExchangeDescription("Bitstamp is a crypto-to-crypto exchange.");
    return exchangeSpecification;
  }
}
