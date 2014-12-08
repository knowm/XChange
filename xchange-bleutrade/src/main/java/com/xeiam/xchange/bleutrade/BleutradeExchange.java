package com.xeiam.xchange.bleutrade;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeAccountService;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeMarketDataService;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeTradeService;

public class BleutradeExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BleutradeExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BleutradeMarketDataService(exchangeSpecification);
    this.pollingAccountService = new BleutradeAccountService(exchangeSpecification);
    this.pollingTradeService = new BleutradeTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bleutrade.com/api/");
    exchangeSpecification.setHost("bleutrade.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bleutrade");
    exchangeSpecification.setExchangeDescription("Bleutrade is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }
}
