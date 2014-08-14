package com.xeiam.xchange.bitbay;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.service.polling.BitbayMarketDataService;

/**
 * @author kpysniak
 */
public class BitbayExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://market.bitbay.pl/API/Public");
    exchangeSpecification.setHost("bitbay.pl");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitbay");
    exchangeSpecification.setExchangeDescription("Bitbay is a Bitcoin exchange based in Katowice, Poland.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitbayMarketDataService(exchangeSpecification);
    this.pollingTradeService = null;
    this.pollingAccountService = null;
  }

}
