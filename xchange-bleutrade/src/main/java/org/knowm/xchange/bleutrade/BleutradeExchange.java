package org.knowm.xchange.bleutrade;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.service.polling.BleutradeAccountService;
import org.knowm.xchange.bleutrade.service.polling.BleutradeMarketDataService;
import org.knowm.xchange.bleutrade.service.polling.BleutradeMarketDataServiceRaw;
import org.knowm.xchange.bleutrade.service.polling.BleutradeTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BleutradeExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BleutradeMarketDataService(this);
    this.pollingAccountService = new BleutradeAccountService(this);
    this.pollingTradeService = new BleutradeTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException {
    List<BleutradeCurrency> currencies = ((BleutradeMarketDataServiceRaw) pollingMarketDataService).getBleutradeCurrencies();
    List<BleutradeMarket> markets = ((BleutradeMarketDataServiceRaw) pollingMarketDataService).getBleutradeMarkets();
    metaData = BleutradeAdapters.adaptToExchangeMetaData(currencies, markets);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bleutrade.com/api/");
    exchangeSpecification.setHost("bleutrade.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bleutrade");
    exchangeSpecification.setExchangeDescription("Bleutrade is a bitcoin and altcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
