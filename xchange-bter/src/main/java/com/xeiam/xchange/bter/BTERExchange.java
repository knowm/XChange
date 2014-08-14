package com.xeiam.xchange.bter;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bter.service.polling.BTERPollingAccountService;
import com.xeiam.xchange.bter.service.polling.BTERPollingMarketDataService;
import com.xeiam.xchange.bter.service.polling.BTERPollingTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Bter exchange API</li>
 * </ul>
 */
public class BTERExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BTERExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BTERPollingMarketDataService(exchangeSpecification);
    this.pollingAccountService = new BTERPollingAccountService(exchangeSpecification);
    this.pollingTradeService = new BTERPollingTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.bter.com");
    exchangeSpecification.setHost("bter.com");
    exchangeSpecification.setExchangeName("BTER");

    return exchangeSpecification;
  }

}
