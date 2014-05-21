package com.xeiam.xchange;

import com.xeiam.xchange.service.polling.HitbtcMarketDataService;

/**
 * @author kpysniak
 */
public class HitbtcExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("http://api.hitbtc.com/api/1/public");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new HitbtcMarketDataService(exchangeSpecification);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

}
