package com.xeiam.xchange.vircurex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vircurex.service.polling.VircurexAccountService;
import com.xeiam.xchange.vircurex.service.polling.VircurexMarketDataService;
import com.xeiam.xchange.vircurex.service.polling.VircurexTradeService;

public class VircurexExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new VircurexMarketDataService(this);
    this.pollingAccountService = new VircurexAccountService(this);
    this.pollingTradeService = new VircurexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vircurex.com");
    exchangeSpecification.setExchangeName("Vircurex");

    return exchangeSpecification;
  }

}
