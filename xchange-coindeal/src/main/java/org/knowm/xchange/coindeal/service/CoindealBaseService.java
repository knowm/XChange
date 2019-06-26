package org.knowm.xchange.coindeal.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.CoindealAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class CoindealBaseService extends BaseExchangeService implements BaseService {

  protected CoindealAuthenticated coindeal;
  protected ParamsDigest basicAuthentication;

  public CoindealBaseService(Exchange exchange) {
    super(exchange);
    coindeal =
        RestProxyFactory.createProxy(
            CoindealAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    basicAuthentication =
        CoindealDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
