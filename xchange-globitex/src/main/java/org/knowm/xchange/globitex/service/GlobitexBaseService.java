package org.knowm.xchange.globitex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.globitex.GlobitexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class GlobitexBaseService extends BaseExchangeService implements BaseService {

  protected GlobitexAuthenticated globitex;
  protected ParamsDigest signatureCreator;

  public GlobitexBaseService(Exchange exchange) {
    super(exchange);

    globitex =
        RestProxyFactory.createProxy(
            GlobitexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    signatureCreator =
        GlobitexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
