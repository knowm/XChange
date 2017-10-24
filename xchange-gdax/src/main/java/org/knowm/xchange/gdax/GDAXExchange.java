package org.knowm.xchange.gdax;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import org.knowm.xchange.gdax.service.GDAXAccountService;
import org.knowm.xchange.gdax.service.GDAXMarketDataService;
import org.knowm.xchange.gdax.service.GDAXMarketDataServiceRaw;
import org.knowm.xchange.gdax.service.GDAXTradeService;
import org.knowm.xchange.utils.nonce.CurrentTime1000NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class GDAXExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime1000NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new GDAXMarketDataService(this);
    this.accountService = new GDAXAccountService(this);
    this.tradeService = new GDAXTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.gdax.com");
    exchangeSpecification.setHost("api.gdax.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("GDAX");
    exchangeSpecification.setExchangeDescription("GDAX Exchange is a Bitcoin exchange recently launched in January 2015");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    List<GDAXProduct> products = ((GDAXMarketDataServiceRaw) marketDataService).getCoinbaseExProducts();
    exchangeMetaData = GDAXAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
  }
}
