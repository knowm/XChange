package com.xeiam.xchange.hitbtc;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcTradeService;
import si.mazi.rescu.NonceFactory;
import si.mazi.rescu.ValueFactory;

/**
 * @author kpysniak
 */
public class HitbtcExchange extends BaseExchange implements Exchange {

  private final ValueFactory<Long> nonceFactory = new NonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("http://api.hitbtc.com");
    exchangeSpecification.setHost("hitbtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Hitbtc");
    exchangeSpecification.setExchangeDescription("Hitbtc is a Bitcoin exchange.");
    exchangeSpecification.setExchangeSpecificParametersItem("demo-api", "http://demo-api.hitbtc.com");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new HitbtcMarketDataService(exchangeSpecification, nonceFactory);
    this.pollingTradeService = new HitbtcTradeService(exchangeSpecification, nonceFactory);
    this.pollingAccountService = new HitbtcAccountService(exchangeSpecification, nonceFactory);
  }

}
