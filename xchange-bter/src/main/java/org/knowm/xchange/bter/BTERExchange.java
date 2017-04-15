package org.knowm.xchange.bter;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bter.dto.marketdata.BTERMarketInfoWrapper.BTERMarketInfo;
import org.knowm.xchange.bter.service.BTERAccountService;
import org.knowm.xchange.bter.service.BTERMarketDataService;
import org.knowm.xchange.bter.service.BTERMarketDataServiceRaw;
import org.knowm.xchange.bter.service.BTERTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BTERExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BTERMarketDataService(this);
    this.accountService = new BTERAccountService(this);
    this.tradeService = new BTERTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.bter.com");
    exchangeSpecification.setHost("bter.com");
    exchangeSpecification.setExchangeName("BTER");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    Map<CurrencyPair, BTERMarketInfo> currencyPair2BTERMarketInfoMap = ((BTERMarketDataServiceRaw) marketDataService).getBTERMarketInfo();
    exchangeMetaData = BTERAdapters.adaptToExchangeMetaData(currencyPair2BTERMarketInfoMap);
  }
}
