package org.knowm.xchange.exmo;

import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exmo.service.ExmoAccountService;
import org.knowm.xchange.exmo.service.ExmoMarketDataService;
import org.knowm.xchange.exmo.service.ExmoTradeService;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class ExmoExchange extends BaseExchange implements Exchange {
  private SynchronizedValueFactory<Long> nonceFactory =
      new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new ExmoMarketDataService(this);
    this.accountService = new ExmoAccountService(this);
    this.tradeService = new ExmoTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setShouldLoadRemoteMetaData(false);
    exchangeSpecification.setSslUri("https://api.exmo.com");
    exchangeSpecification.setHost("exmo.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("exmo");
    exchangeSpecification.setExchangeDescription("exmo");
    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    ((ExmoMarketDataService) marketDataService).updateMetadata(exchangeMetaData);
  }

  @Override
  protected void loadExchangeMetaData(InputStream is) {
    exchangeMetaData = loadMetaData(is, ExchangeMetaData.class);
  }
}
