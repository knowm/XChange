package com.xeiam.xchange.mintpal;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.mintpal.service.polling.MintPalMarketDataService;

/**
 * @author jamespedwards42
 */
public class MintPalExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(final ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new MintPalMarketDataService(exchangeSpecification);

  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.mintpal.com");
    exchangeSpecification.setExchangeName("MintPal");
    exchangeSpecification.setExchangeDescription("MintPal Limited is a UK based private company (registered UK company #09009856) that focuses on the exchanging of cryptocurrencies. ");

    return exchangeSpecification;
  }
}
