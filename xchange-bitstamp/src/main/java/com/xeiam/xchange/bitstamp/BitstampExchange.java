package com.xeiam.xchange.bitstamp;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.polling.BitstampPollingMarketDataService;

/**
 * @author Matija Mazi <br/>
 * @created 12/30/12 9:36 PM
 */
public class BitstampExchange extends BaseExchange implements Exchange {
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setUri("https://www.bitstamp.net");
    exchangeSpecification.setVersion("");
    exchangeSpecification.setHost("www.bitstamp.net");
    exchangeSpecification.setPort(80);
    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.pollingMarketDataService = new BitstampPollingMarketDataService(exchangeSpecification);
  }

}
