package com.xeiam.xchange.bitcurex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataService;

public class BitcurexExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BitcurexExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcurexMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcurex.com");
    exchangeSpecification.setPort(8080);
    exchangeSpecification.setHost("bitcurex.com");
    exchangeSpecification.setExchangeName("Bitcurex");
    exchangeSpecification.setExchangeDescription("Bitcurex is a polish Bitcoin exchange");

    return exchangeSpecification;
  }

}
