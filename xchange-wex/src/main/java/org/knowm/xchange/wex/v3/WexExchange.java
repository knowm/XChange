package org.knowm.xchange.wex.v3;

import java.io.InputStream;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.TimestampIncrementingNonceFactory;
import org.knowm.xchange.wex.v3.dto.marketdata.WexExchangeInfo;
import org.knowm.xchange.wex.v3.dto.meta.WexMetaData;
import org.knowm.xchange.wex.v3.service.WexAccountService;
import org.knowm.xchange.wex.v3.service.WexMarketDataService;
import org.knowm.xchange.wex.v3.service.WexTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class WexExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new TimestampIncrementingNonceFactory();
  private WexMetaData wexMetaData;
  private WexExchangeInfo wexExchangeInfo;

  @Override
  protected void initServices() {

    this.marketDataService = new WexMarketDataService(this);
    this.accountService = new WexAccountService(this);
    this.tradeService = new WexTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://wex.nz");
    exchangeSpecification.setHost("wex.nz");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Wex");
    exchangeSpecification.setExchangeDescription(
    		"Wex is a Bitcoin exchange registered in NZ.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {
    wexMetaData = loadMetaData(is, WexMetaData.class);
    exchangeMetaData = WexAdapters.toMetaData(null, wexMetaData);
  }

  @Override
  public void remoteInit() {
    try {
      WexMarketDataService marketDataService = (WexMarketDataService) this.marketDataService;
      wexExchangeInfo = marketDataService.getBTCEInfo();
      exchangeMetaData = WexAdapters.toMetaData(wexExchangeInfo, wexMetaData);
    } catch (Exception e) {
      logger.warn(
          "An exception occurred while loading the metadata file from the file. This may lead to unexpected results.",
          e);
    }
  }

  public WexMetaData getWexMetaData() {
    return wexMetaData;
  }

  public WexExchangeInfo getWexExchangeInfo() {
    return wexExchangeInfo;
  }
}
