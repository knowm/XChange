package com.xeiam.xchange.anx.v2;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.v2.service.polling.ANXAccountService;
import com.xeiam.xchange.anx.v2.service.polling.ANXMarketDataService;
import com.xeiam.xchange.anx.v2.service.polling.ANXTradeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

public class ANXExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    // Configure the basic services if configuration does not apply
    this.pollingMarketDataService = new ANXMarketDataService(this);
    this.pollingTradeService = new ANXTradeService(this, nonceFactory);
    this.pollingAccountService = new ANXAccountService(this, nonceFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://anxpro.com");
    exchangeSpecification.setHost("anxpro.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ANXPRO");
    exchangeSpecification.setExchangeDescription("Asia Nexgen is a Bitcoin exchange registered in Hong Kong.");

    return exchangeSpecification;
  }

}
