package com.xeiam.xchange.kraken;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.kraken.service.polling.KrakenAccountService;
import com.xeiam.xchange.kraken.service.polling.KrakenMarketDataService;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeService;

/**
 * @author Benedikt Bünz
 */
public class KrakenExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    // Configure the basic services if configuration does not apply
    this.pollingMarketDataService = new KrakenMarketDataService(exchangeSpecification);
    this.pollingTradeService = new KrakenTradeService(exchangeSpecification);
    this.pollingAccountService = new KrakenAccountService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.kraken.com");
    exchangeSpecification.setHost("api.kraken.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Kraken");
    exchangeSpecification.setExchangeDescription("Kraken is a Bitcoin exchange operated by Payward, Inc.");
    return exchangeSpecification;
  }

}
