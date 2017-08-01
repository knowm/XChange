package org.knowm.xchange.bleutrade;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeCurrency;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeMarket;
import org.knowm.xchange.bleutrade.service.BleutradeAccountService;
import org.knowm.xchange.bleutrade.service.BleutradeMarketDataService;
import org.knowm.xchange.bleutrade.service.BleutradeMarketDataServiceRaw;
import org.knowm.xchange.bleutrade.service.BleutradeTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.List;

public class BleutradeExchange extends BaseExchange implements Exchange {

  //until bluetrade offer more than one api key, it is invalid to create more than one nonce
  private static SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BleutradeMarketDataService(this);
    this.accountService = new BleutradeAccountService(this);
    this.tradeService = new BleutradeTradeService(this);
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

  @Override
  public void remoteInit() throws IOException {
    List<BleutradeCurrency> currencies = ((BleutradeMarketDataServiceRaw) marketDataService).getBleutradeCurrencies();
    List<BleutradeMarket> markets = ((BleutradeMarketDataServiceRaw) marketDataService).getBleutradeMarkets();
    exchangeMetaData = BleutradeAdapters.adaptToExchangeMetaData(currencies, markets);
  }
}
