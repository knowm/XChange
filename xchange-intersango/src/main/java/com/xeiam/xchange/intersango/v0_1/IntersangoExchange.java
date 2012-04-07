package com.xeiam.xchange.intersango.v0_1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.intersango.v0_1.service.marketdata.IntersangoPublicHttpMarketDataService;
import com.xeiam.xchange.intersango.v0_1.service.trade.IntersangoTradeService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the MtGox BTC exchange API</li>
 * </ul>
 * 
 * @since 0.0.1  
 */
public class IntersangoExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public IntersangoExchange() {
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    if (exchangeSpecification == null) {
      exchangeSpecification = getDefaultExchangeSpecification();
    }
    this.marketDataService = new IntersangoPublicHttpMarketDataService(exchangeSpecification);
    this.tradeService = new IntersangoTradeService(exchangeSpecification);
    this.streamingMarketDataService = new IntersangoStreamingMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setUri("https://intersango.com");
    exchangeSpecification.setVersion("1");
    exchangeSpecification.setHost("intersango.com");
    exchangeSpecification.setPort(1337);

    return exchangeSpecification;
  }

}
