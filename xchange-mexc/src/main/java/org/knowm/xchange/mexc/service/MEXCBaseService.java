package org.knowm.xchange.mexc.service;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.mexc.MEXCAuthenticated;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class MEXCBaseService implements BaseService {

  protected final MEXCAuthenticated mexcAuthenticated;
  protected final ParamsDigest signatureCreator;
  protected final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);
  protected final String apiKey;

  public MEXCBaseService(Exchange exchange) {
    mexcAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                MEXCAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        MEXCDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    apiKey = exchange.getExchangeSpecification().getApiKey();
  }
}
