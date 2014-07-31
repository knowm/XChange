package com.xeiam.xchange.bitcurex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcurex.service.polling.BitcurexMarketDataService;
import com.xeiam.xchange.currency.Currencies;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Bitcurex exchange API</li>
 * </ul>
 */
public class BitcurexExchange extends BaseExchange implements Exchange {

  public static final String KEY_CURRENCY = "CURRENCY";

  /**
   * Default constructor for ExchangeFactory
   */
  public BitcurexExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcurexMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://eur.bitcurex.com");
    exchangeSpecification.setHost("eur.bitcurex.com");
    exchangeSpecification.setExchangeName("Bitcurex EUR");
    exchangeSpecification.setExchangeDescription("Bitcurex is a polish Bitcoin exchange");
    exchangeSpecification.getExchangeSpecificParameters().put(KEY_CURRENCY, Currencies.EUR);

    return exchangeSpecification;
  }

  public ExchangeSpecification getDefaultExchangePLNSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://pln.bitcurex.com");
    exchangeSpecification.setHost("pln.bitcurex.com");
    exchangeSpecification.setExchangeName("Bitcurex PLN");
    exchangeSpecification.setExchangeDescription("Bitcurex is a polish Bitcoin exchange");
    exchangeSpecification.getExchangeSpecificParameters().put(KEY_CURRENCY, Currencies.PLN);

    return exchangeSpecification;
  }
}
