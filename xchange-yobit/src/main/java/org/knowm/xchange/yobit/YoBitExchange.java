package org.knowm.xchange.yobit;

import java.io.IOException;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import org.knowm.xchange.yobit.dto.marketdata.YoBitInfo;
import org.knowm.xchange.yobit.service.YoBitMarketDataService;
import org.knowm.xchange.yobit.service.YoBitMarketDataServiceRaw;

import si.mazi.rescu.SynchronizedValueFactory;

public class YoBitExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://yobit.net");
    exchangeSpecification.setHost("yobit.net");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("YoBit");
    exchangeSpecification.setExchangeDescription("YoBit.Net - Ethereum (ETH) Exchange");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new YoBitMarketDataService(this);
    this.accountService = null; // new LIVECOINAccountService(this);
    this.tradeService = null; // new LIVECOINTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException {
    YoBitInfo products = ((YoBitMarketDataServiceRaw) marketDataService).getProducts();
    exchangeMetaData = YoBitAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
    //System.out.println("JSON: " + ObjectMapperHelper.toJSON(exchangeMetaData));
  }

}
