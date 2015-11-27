package com.xeiam.xchange.poloniex;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexCurrencyInfo;
import com.xeiam.xchange.poloniex.dto.marketdata.PoloniexMarketData;
import com.xeiam.xchange.poloniex.service.polling.PoloniexAccountService;
import com.xeiam.xchange.poloniex.service.polling.PoloniexMarketDataService;
import com.xeiam.xchange.poloniex.service.polling.PoloniexMarketDataServiceRaw;
import com.xeiam.xchange.poloniex.service.polling.PoloniexTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author Zach Holmes
 */

public class PoloniexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new PoloniexMarketDataService(this);
    this.pollingAccountService = new PoloniexAccountService(this);
    this.pollingTradeService = new PoloniexTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException {

    PoloniexMarketDataServiceRaw poloniexMarketDataServiceRaw = (PoloniexMarketDataServiceRaw)pollingMarketDataService;

    Map<String, PoloniexCurrencyInfo> poloniexCurrencyInfoMap = poloniexMarketDataServiceRaw.getPoloniexCurrencyInfo();
    Map<String, PoloniexMarketData> poloniexMarketDataMap = poloniexMarketDataServiceRaw.getAllPoloniexTickers();

    metaData = PoloniexAdapters.adaptToExchangeMetaData(poloniexCurrencyInfoMap, poloniexMarketDataMap, metaData);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://poloniex.com/");
    exchangeSpecification.setHost("poloniex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Poloniex");
    exchangeSpecification.setExchangeDescription("Poloniex is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
