package org.knowm.xchange.gdax;

import java.io.IOException;

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
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);

    this.marketDataService = new GDAXMarketDataService(this);
    this.accountService = new GDAXAccountService(this);
    this.tradeService = new GDAXTradeService(this);
  }

  /**
   * Adjust host parameters depending on exchange specific parameters
   */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true)) {

        exchangeSpecification.setSslUri("https://api-public.sandbox.gdax.com");
        exchangeSpecification.setHost("api-public.sandbox.gdax.com");

      }
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.gdax.com");
    exchangeSpecification.setHost("api.gdax.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("GDAX");
    exchangeSpecification.setExchangeDescription("GDAX Exchange is a Bitcoin exchange recently launched in January 2015");

    exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", false);

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    GDAXProduct[] products = ((GDAXMarketDataServiceRaw) marketDataService).getGDAXProducts();
    exchangeMetaData = GDAXAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
  }
}
