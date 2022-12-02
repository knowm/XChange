package org.knowm.xchange.fcoin.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.fcoin.FCoin;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class FCoinBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final FCoin fcoin;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public FCoinBaseService(Exchange exchange) {

    super(exchange);
    apiKey = exchange.getExchangeSpecification().getApiKey();
    fcoin =
        ExchangeRestProxyBuilder.forInterface(FCoin.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        FCoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(), apiKey);
  }
}
