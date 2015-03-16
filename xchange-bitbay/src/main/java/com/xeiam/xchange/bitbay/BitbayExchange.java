package com.xeiam.xchange.bitbay;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.service.polling.BitbayAccountService;
import com.xeiam.xchange.bitbay.service.polling.BitbayMarketDataService;
import com.xeiam.xchange.bitbay.service.polling.BitbayTradeService;

/**
 * @author kpysniak
 */
public class BitbayExchange extends BaseExchange implements Exchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://market.bitbay.pl");
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
    this.pollingTradeService = new BitbayTradeService(exchangeSpecification);
    this.pollingAccountService = new BitbayAccountService(exchangeSpecification);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }

}
