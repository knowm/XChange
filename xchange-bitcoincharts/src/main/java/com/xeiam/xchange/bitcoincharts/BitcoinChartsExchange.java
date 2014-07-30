package com.xeiam.xchange.bitcoincharts;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincharts.service.polling.BitcoinChartsMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the BitcoinCharts API</li>
 * </ul>
 */
public class BitcoinChartsExchange extends BaseExchange implements Exchange {

  /**
   * Constructor
   */
  public BitcoinChartsExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoinChartsMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://api.bitcoincharts.com");
    exchangeSpecification.setHost("api.bitcoincharts.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin Charts");
    exchangeSpecification.setExchangeDescription("Bitcoin charts provides financial and technical data related to the Bitcoin network.");

    return exchangeSpecification;
  }
}
