package com.xeiam.xchange.hitbtc;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcAccountService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcMarketDataService;
import com.xeiam.xchange.hitbtc.service.polling.HitbtcTradeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

public class HitbtcExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.hitbtc.com");
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

    this.pollingMarketDataService = new HitbtcMarketDataService(this, nonceFactory);
    HitbtcTradeService hitbtcTradeService = new HitbtcTradeService(this, nonceFactory);
    HitbtcAccountService hitbtcAccountService = new HitbtcAccountService(this, nonceFactory);
    this.pollingTradeService = hitbtcTradeService;
    this.pollingAccountService = hitbtcAccountService;
  }

}
