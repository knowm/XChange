package com.xeiam.xchange.bitstamp;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.api.BitStamp;
import com.xeiam.xchange.bitstamp.api.BitstampFactory;
import com.xeiam.xchange.bitstamp.polling.BitstampPollingAccountService;
import com.xeiam.xchange.bitstamp.polling.BitstampPollingMarketDataService;
import com.xeiam.xchange.bitstamp.polling.BitstampPollingTradeService;

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
    BitStamp bitstampEndpoint = BitstampFactory.createResteasyEndpoint();
    this.pollingMarketDataService = new BitstampPollingMarketDataService(exchangeSpecification, bitstampEndpoint);
    this.pollingTradeService = new BitstampPollingTradeService(exchangeSpecification, bitstampEndpoint);
    this.pollingAccountService = new BitstampPollingAccountService(exchangeSpecification, bitstampEndpoint);
  }

}
