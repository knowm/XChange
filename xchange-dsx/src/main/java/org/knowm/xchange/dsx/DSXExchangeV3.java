package org.knowm.xchange.dsx;

import java.io.InputStream;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dsx.dto.marketdata.DSXExchangeInfo;
import org.knowm.xchange.dsx.dto.meta.DSXMetaData;
import org.knowm.xchange.dsx.service.DSXAccountServiceV3;
import org.knowm.xchange.dsx.service.DSXMarketDataServiceV3;
import org.knowm.xchange.dsx.service.DSXTradeServiceV3;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * DSX Exchange V3 implementation
 *
 * @author Pavel Chertalev
 */
public class DSXExchangeV3 extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongCurrentTimeIncrementalNonceFactory();
  private DSXMetaData dsxMetaData;
  private DSXExchangeInfo dsxExchangeInfo;

  @Override
  protected void initServices() {
    this.marketDataService = new DSXMarketDataServiceV3(this);
    this.tradeService = new DSXTradeServiceV3(this);
    this.accountService = new DSXAccountServiceV3(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {

    dsxMetaData = loadMetaData(is, DSXMetaData.class);
    exchangeMetaData = DSXAdapters.toMetaData(null, dsxMetaData);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://dsx.uk");
    exchangeSpecification.setHost("dsx.uk");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("DSX");
    exchangeSpecification.setExchangeDescription(
        "DSX the UK first Digital Securities Exchange run by the FCA regulated ePayments.");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() {
    try {
      DSXMarketDataServiceV3 marketDataService = (DSXMarketDataServiceV3) this.marketDataService;
      dsxExchangeInfo = marketDataService.getDSXInfo();
      exchangeMetaData = DSXAdapters.toMetaData(dsxExchangeInfo, dsxMetaData);
    } catch (Exception e) {
      logger.warn("An exception occurred while loading the metadata", e);
    }
  }

  public DSXMetaData getDsxMetaData() {

    return dsxMetaData;
  }

  public DSXExchangeInfo getDsxExchangeInfo() {

    return dsxExchangeInfo;
  }
}
