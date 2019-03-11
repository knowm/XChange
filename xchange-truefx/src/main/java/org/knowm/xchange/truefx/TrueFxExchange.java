package org.knowm.xchange.truefx;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.truefx.service.TrueFxMarketDataService;
import si.mazi.rescu.SynchronizedValueFactory;

public class TrueFxExchange extends BaseExchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setPlainTextUri("http://webrates.truefx.com/");
    specification.setExchangeName("TrueFX");
    specification.setExchangeDescription("TrueFX exchange");
    return specification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new TrueFxMarketDataService(this);
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // there are no suitable API calls to use for remote init
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return null;
  }
}
