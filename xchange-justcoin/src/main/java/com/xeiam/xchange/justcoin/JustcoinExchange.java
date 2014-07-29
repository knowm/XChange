package com.xeiam.xchange.justcoin;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.justcoin.service.polling.JustcoinAccountService;
import com.xeiam.xchange.justcoin.service.polling.JustcoinMarketDataService;
import com.xeiam.xchange.justcoin.service.polling.JustcoinTradeService;

/**
 * @author jamespedwards42
 */
public class JustcoinExchange extends BaseExchange implements Exchange {

  /**
   * Constructor
   */
  public JustcoinExchange() {

  }

  @Override
  public void applySpecification(final ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new JustcoinMarketDataService(exchangeSpecification);
    this.pollingAccountService = new JustcoinAccountService(exchangeSpecification);
    this.pollingTradeService = new JustcoinTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://justcoin.com");
    exchangeSpecification.setExchangeName("Justcoin");
    exchangeSpecification.setExchangeDescription("Justcoin is a digital currency exchange, founded in 2013 with its office in Oslo, Norway.");

    return exchangeSpecification;
  }
}
