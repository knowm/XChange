package org.knowm.xchange.bitcoincharts;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import org.knowm.xchange.bitcoincharts.service.BitcoinChartsMarketDataService;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoinChartsExchange extends BaseExchange implements Exchange {

  /** Constructor */
  public BitcoinChartsExchange() {}

  @Override
  protected void initServices() {

    this.marketDataService = new BitcoinChartsMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setPlainTextUri("http://api.bitcoincharts.com");
    exchangeSpecification.setHost("api.bitcoincharts.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitcoinCharts");
    exchangeSpecification.setExchangeDescription(
        "Bitcoin charts provides financial and technical data related to the Bitcoin network.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {

    BitcoinChartsTicker[] tickers =
        ((BitcoinChartsMarketDataService) marketDataService).getBitcoinChartsTickers();
    exchangeMetaData = BitcoinChartsAdapters.adaptMetaData(exchangeMetaData, tickers);
    // String json = ObjectMapperHelper.toJSON(exchangeMetaData);
    // System.out.println("json: " + json);
  }
}
