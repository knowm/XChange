package org.knowm.xchange.globitex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.globitex.GlobitexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class GlobitexBaseService extends BaseExchangeService implements BaseService {

  protected GlobitexAuthenticated globitex;
  protected ParamsDigest signatureCreator;

  public GlobitexBaseService(Exchange exchange) {
    super(exchange);

    globitex =
        ExchangeRestProxyBuilder.forInterface(
                GlobitexAuthenticated.class, exchange.getExchangeSpecification())
            .build();

    signatureCreator =
        GlobitexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
