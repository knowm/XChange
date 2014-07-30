package com.xeiam.xchange.virtex.v2;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.virtex.v2.service.polling.VirtExMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the VirtEx exchange API</li>
 * </ul>
 */
public class VirtExExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public VirtExExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new VirtExMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://cavirtex.com");
    exchangeSpecification.setHost("cavirtex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("VirtEx");
    exchangeSpecification.setExchangeDescription("CAVirtex is a Bitcoin exchange registered in Canada.");

    return exchangeSpecification;
  }
}
