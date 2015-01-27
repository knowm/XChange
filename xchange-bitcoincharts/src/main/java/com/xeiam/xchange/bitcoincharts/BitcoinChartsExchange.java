package com.xeiam.xchange.bitcoincharts;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincharts.service.polling.BitcoinChartsMarketDataService;

public class BitcoinChartsExchange extends BaseExchange implements Exchange {

  /**
   * Constructor
   */
  public BitcoinChartsExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitcoinChartsMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://api.bitcoincharts.com");
    exchangeSpecification.setHost("api.bitcoincharts.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitcoinCharts");
    exchangeSpecification.setExchangeDescription("Bitcoin charts provides financial and technical data related to the Bitcoin network.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }
}
