package com.xeiam.xchange.bittrex.v1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexAccountService;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexMarketDataService;
import com.xeiam.xchange.bittrex.v1.service.polling.BittrexTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the Bittrex exchange API</li>
 * </ul>
 */
public class BittrexExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public BittrexExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new BittrexMarketDataService(exchangeSpecification);
    this.pollingAccountService = new BittrexAccountService(exchangeSpecification);
    this.pollingTradeService = new BittrexTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bittrex.com/api/");
    exchangeSpecification.setHost("bittrex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bittrex");
    exchangeSpecification.setExchangeDescription("Bittrex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }
}