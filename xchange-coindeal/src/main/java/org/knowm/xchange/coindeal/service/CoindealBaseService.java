package org.knowm.xchange.coindeal.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coindeal.CoindealAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class CoindealBaseService extends BaseExchangeService implements BaseService {

  protected CoindealAuthenticated coindeal;
  protected ParamsDigest basicAuthentication;

  public CoindealBaseService(Exchange exchange) {
    super(exchange);
    coindeal =
        ExchangeRestProxyBuilder.forInterface(
                CoindealAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    basicAuthentication =
        CoindealDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
  }
}
