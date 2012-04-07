package com.xeiam.xchange.imcex.v1;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.imcex.v1.service.marketdata.ImcexPublicHttpMarketDataService;
import com.xeiam.xchange.imcex.v1.service.trader.ImcexAccountService;
import com.xeiam.xchange.service.marketdata.streaming.StreamingMarketDataService;

/**
 * <p>Exchange implementation to provide the following to applications:</p>
 * <ul>
 * <li>A wrapper for the MtGox BTC exchange API</li>
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
    this.tradeService = new ImcexAccountService(exchangeSpecification);
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return null;
  }

  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setUri("https://imcex.com");
    exchangeSpecification.setVersion("1");

    return exchangeSpecification;
  }
}
