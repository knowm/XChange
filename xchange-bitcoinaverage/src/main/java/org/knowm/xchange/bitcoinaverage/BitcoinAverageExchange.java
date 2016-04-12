package org.knowm.xchange.bitcoinaverage;

import java.io.IOException;
import java.io.InputStream;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTickers;
import org.knowm.xchange.bitcoinaverage.dto.meta.BitcoinAverageMetaData;
import org.knowm.xchange.bitcoinaverage.service.polling.BitcoinAverageMarketDataService;
import org.knowm.xchange.bitcoinaverage.service.polling.BitcoinAverageMarketDataServiceRaw;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoinAverageExchange extends BaseExchange implements Exchange {

  private BitcoinAverageMetaData bitcoinAverageMetaData;

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BitcoinAverageMarketDataService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitcoinaverage.com");
    exchangeSpecification.setHost("bitcoinaverage.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitcoin Average");
    exchangeSpecification
        .setExchangeDescription("Bitcoin Average provides a more accurate price of bitcoin using weighted average for multiple exchanges.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    // No private API implemented. Not needed for this exchange at the moment.
    return null;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    BitcoinAverageTickers tickers = ((BitcoinAverageMarketDataServiceRaw) pollingMarketDataService).getBitcoinAverageAllTickers();
    metaData = BitcoinAverageAdapters.adaptMetaData(tickers, bitcoinAverageMetaData);
  }

  @Override
  protected void loadMetaData(InputStream is) {
    bitcoinAverageMetaData = loadMetaData(is, BitcoinAverageMetaData.class);
  }

  public BitcoinAverageMetaData getBitcoinAverageMetaData() {
    return bitcoinAverageMetaData;
  }
}
