package com.xeiam.xchange.imcex.v1;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.exchange.BaseExchange;
import com.xeiam.xchange.imcex.v1.service.marketdata.ImcexPublicHttpMarketDataService;
import com.xeiam.xchange.imcex.v1.service.trader.ImcexAccountService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Exchange implementation to provide the following to applications:</p>
 * <ul>
 * <li>A wrapper for the MtGox Bitcoin exchange API</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public class ImcexExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public ImcexExchange() {
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.marketDataService = new ImcexPublicHttpMarketDataService(exchangeSpecification);
    this.accountService = new ImcexAccountService(exchangeSpecification);
  }

  public ExchangeSpecification getDefaultExchangeSpecification() {

    Map<String, Object> parameters = new HashMap<String, Object>();

    parameters.put(ExchangeSpecification.API_URI, "https://mtgox.com");
    parameters.put(ExchangeSpecification.API_VERSION, "1");

    return new ExchangeSpecification(this.getClass().getCanonicalName(), parameters);
  }
}
