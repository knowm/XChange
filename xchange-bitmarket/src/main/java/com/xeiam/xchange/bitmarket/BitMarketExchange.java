package com.xeiam.xchange.bitmarket;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitmarket.service.polling.BitMarketDataService;

/**
 * @author kpysniak
 */
public class BitMarketExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.bitmarket.pl/json");
    exchangeSpecification.setHost("www.bitmarket.pl");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitmarket");
    exchangeSpecification.setExchangeDescription("Bitmarket is a Bitcoin exchange based in Poland.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitMarketDataService(exchangeSpecification);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

}
