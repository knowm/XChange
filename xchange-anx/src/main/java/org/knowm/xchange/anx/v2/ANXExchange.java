package org.knowm.xchange.anx.v2;

import java.io.InputStream;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.anx.v2.dto.meta.ANXMetaData;
import org.knowm.xchange.anx.v2.service.polling.ANXAccountService;
import org.knowm.xchange.anx.v2.service.polling.ANXMarketDataService;
import org.knowm.xchange.anx.v2.service.polling.ANXTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class ANXExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    // Configure the basic services if configuration does not apply
    this.pollingMarketDataService = new ANXMarketDataService(this);
    this.pollingTradeService = new ANXTradeService(this);
    this.pollingAccountService = new ANXAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://anxpro.com");
    exchangeSpecification.setHost("anxpro.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ANXPRO");
    exchangeSpecification.setExchangeDescription("Asia Nexgen is a Bitcoin exchange registered in Hong Kong.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  private ANXMetaData anxMetaData;

  public ANXMetaData getANXMetaData() {
    return anxMetaData;
  }

  @Override
  protected void loadMetaData(InputStream is) {
    anxMetaData = loadMetaData(is, ANXMetaData.class);
    metaData = anxMetaData;
  }
}
