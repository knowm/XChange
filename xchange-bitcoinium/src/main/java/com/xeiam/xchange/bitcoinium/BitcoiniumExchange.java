package com.xeiam.xchange.bitcoinium;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinium.service.polling.BitcoiniumMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Bitcoinium Web Service API</li>
 * </ul>
 */
public class BitcoiniumExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BitcoiniumExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoiniumMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    // exchangeSpecification.setPlainTextUri("http://173.10.241.154:9090");
    exchangeSpecification.setPlainTextUri("http://127.0.0.1:9090");
    // exchangeSpecification.setHost("173.10.241.154:9090");
    exchangeSpecification.setHost("127.0.0.1:9090");
    exchangeSpecification.setPort(9090);
    exchangeSpecification.setExchangeName("Bitcoinium");
    exchangeSpecification.setExchangeDescription("Bitcoinium Web Service provides compact and filtered data from various exchanges");

    return exchangeSpecification;
  }
}
