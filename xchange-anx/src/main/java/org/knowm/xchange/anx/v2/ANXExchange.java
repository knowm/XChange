package org.knowm.xchange.anx.v2;

import java.io.InputStream;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.anx.v2.dto.meta.ANXMetaData;
import org.knowm.xchange.anx.v2.service.ANXAccountService;
import org.knowm.xchange.anx.v2.service.ANXMarketDataService;
import org.knowm.xchange.anx.v2.service.ANXTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.RestProxyFactoryImpl;
import si.mazi.rescu.SynchronizedValueFactory;

public class ANXExchange extends BaseExchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private ANXMetaData anxMetaData;

  @Override
  protected void initServices() {
    // Configure the basic services if configuration does not apply
    IRestProxyFactory restProxyFactory = new RestProxyFactoryImpl();
    this.marketDataService = new ANXMarketDataService(this, restProxyFactory);
    this.tradeService = new ANXTradeService(this, restProxyFactory);
    this.accountService = new ANXAccountService(this, restProxyFactory);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://anxpro.com");
    exchangeSpecification.setHost("anxpro.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("ANXPRO");
    exchangeSpecification.setExchangeDescription(
        "Asia Nexgen is a Bitcoin exchange registered in Hong Kong.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  public ANXMetaData getANXMetaData() {
    return anxMetaData;
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {
    anxMetaData = loadMetaData(is, ANXMetaData.class);
    exchangeMetaData = anxMetaData;
  }
}
