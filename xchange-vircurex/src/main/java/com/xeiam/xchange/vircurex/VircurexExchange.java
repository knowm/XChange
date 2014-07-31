package com.xeiam.xchange.vircurex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vircurex.service.polling.VircurexAccountService;
import com.xeiam.xchange.vircurex.service.polling.VircurexMarketDataService;
import com.xeiam.xchange.vircurex.service.polling.VircurexTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Vircurex exchange API</li>
 * </ul>
 */
public class VircurexExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public VircurexExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new VircurexMarketDataService(exchangeSpecification);
    this.pollingAccountService = new VircurexAccountService(exchangeSpecification);
    this.pollingTradeService = new VircurexTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vircurex.com");
    exchangeSpecification.setExchangeName("Vircurex");

    return exchangeSpecification;
  }

}
