package com.xeiam.xchange.oer;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.oer.service.polling.OERMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Open Exchange Rate API</li>
 * </ul>
 */
public class OERExchange extends BaseExchange implements Exchange {

  /**
   * Constructor
   */
  public OERExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new OERMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://openexchangerates.org");
    exchangeSpecification.setHost("openexchangerates.org");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Open Exchange Rates");
    exchangeSpecification.setExchangeDescription("Open Exchange Rates is an exchange rate provider for a wide range of currencies.");

    return exchangeSpecification;
  }
}
