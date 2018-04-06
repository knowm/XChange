package org.knowm.xchange.bx;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bx.dto.marketdata.BxAssetPair;
import org.knowm.xchange.bx.service.BxAccountService;
import org.knowm.xchange.bx.service.BxMarketDataService;
import org.knowm.xchange.bx.service.BxMarketDataServiceRaw;
import org.knowm.xchange.bx.service.BxTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BxExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new BxMarketDataService(this);
    this.tradeService = new BxTradeService(this);
    this.accountService = new BxAccountService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bx.in.th");
    exchangeSpecification.setHost("bx.in.th");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BX");
    exchangeSpecification.setExchangeDescription("BX is operated by Bitcoin Co. Ltd.");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    Map<String, BxAssetPair> assetPairs =
        ((BxMarketDataServiceRaw) marketDataService).getBxAssetPairs();
    exchangeMetaData = BxAdapters.adaptToExchangeMetaData(assetPairs);
  }
}
