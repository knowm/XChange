package com.xeiam.xchange.poloniex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.poloniex.service.polling.PoloniexMarketDataService;

/**
 * @author Zach Holmes
 */

public class PoloniexExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public PoloniexExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new PoloniexMarketDataService(exchangeSpecification);
    // this.pollingAccountService = new BittrexAccountService(exchangeSpecification);
    // this.pollingTradeService = new BittrexTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://poloniex.com/");
    exchangeSpecification.setHost("poloniex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Poloniex");
    exchangeSpecification.setExchangeDescription("Poloniex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

}
