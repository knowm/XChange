package com.xeiam.xchange.cexio;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cexio.service.polling.CexIOAccountService;
import com.xeiam.xchange.cexio.service.polling.CexIOMarketDataService;
import com.xeiam.xchange.cexio.service.polling.CexIOTradeService;

/**
 * Author: brox
 * Since: 2/6/14
 */

public class CexIOExchange extends BaseExchange implements Exchange {

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CexIOMarketDataService(exchangeSpecification);
    this.pollingAccountService = new CexIOAccountService(exchangeSpecification);
    this.pollingTradeService = new CexIOTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://cex.io");
    exchangeSpecification.setHost("cex.io");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cex.IO");
    exchangeSpecification.setExchangeDescription("Cex.IO is a virtual commodities exchange registered in United Kingdom.");

    return exchangeSpecification;
  }

}
