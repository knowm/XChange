package org.knowm.xchange.lgo.service;

import org.knowm.xchange.lgo.Lgo;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class LgoBaseService extends BaseExchangeService<LgoExchange> implements BaseService {

  protected final Lgo proxy;

  protected LgoBaseService(LgoExchange exchange) {
    super(exchange);
    proxy =
        RestProxyFactory.createProxy(
            Lgo.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }
}
