package org.knowm.xchange.bleutrade.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bleutrade.BleutradeAuthenticated;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.IRestProxyFactory;
import si.mazi.rescu.ParamsDigest;

public class BleutradeBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final BleutradeAuthenticated bleutrade;
  protected final ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public BleutradeBaseService(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange);

    this.bleutrade =
        ExchangeRestProxyBuilder.forInterface(
                BleutradeAuthenticated.class, exchange.getExchangeSpecification())
            .restProxyFactory(restProxyFactory)
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        BleutradeDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
