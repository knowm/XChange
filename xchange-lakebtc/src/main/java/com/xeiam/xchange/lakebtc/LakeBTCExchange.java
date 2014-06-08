package com.xeiam.xchange.lakebtc;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCMarketDataService;

/**
 * @author kpysniak
 */
public class LakeBTCExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.LakeBTC.com/api_v1");
    exchangeSpecification.setHost("https://lakebtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("LakeBTC");
    exchangeSpecification.setExchangeDescription("LakeBTC is a Bitcoin exchange for USD and CNY.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new LakeBTCMarketDataService(exchangeSpecification);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

}
