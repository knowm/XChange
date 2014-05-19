package com.xeiam.xchange;

import com.xeiam.xchange.service.polling.BTCCentralMarketDataService;

/**
 * @author kpysniak
 */
public class BTCCentralExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitcoin-central.net/api/v1/data/eur");
    exchangeSpecification.setHost("bitcoin-central.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin-Central");
    exchangeSpecification.setExchangeDescription("Bitcoin-Central is a Bitcoin exchange registered and maintained by a company based in Paris, France.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BTCCentralMarketDataService(exchangeSpecification);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

}
