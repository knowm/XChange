package com.xeiam.xchange.itbit.v1;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitAccountService;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitMarketDataService;
import com.xeiam.xchange.itbit.v1.service.polling.ItBitTradeService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BTCE exchange API</li>
 * </ul>
 */

public class ItBitExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  /**
   * Default constructor for ExchangeFactory
   */
  public ItBitExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new ItBitMarketDataService(exchangeSpecification, nonceFactory);
    this.pollingAccountService = new ItBitAccountService(exchangeSpecification, nonceFactory);
    this.pollingTradeService = new ItBitTradeService(exchangeSpecification, nonceFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.itbit.com");
    exchangeSpecification.setHost("www.itbit.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ItBit");
    exchangeSpecification.setExchangeDescription("ItBit Bitcoin Exchange");
    exchangeSpecification.setExchangeSpecificParametersItem("authHost", " https://api.itbit.com");

    return exchangeSpecification;
  }
}
