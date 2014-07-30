package com.xeiam.xchange.cryptonit.v2;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cryptonit.v2.service.polling.CryptonitMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Cryptonit exchange API</li>
 * </ul>
 */

public class CryptonitExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public CryptonitExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new CryptonitMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://cryptonit.net");
    exchangeSpecification.setHost("cryptonit.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptonit");
    exchangeSpecification.setExchangeDescription("Cryptonit is a cryptocurrency market owned and operated by UK based company Cryptonit Solutions Ltd.");

    return exchangeSpecification;
  }
}
