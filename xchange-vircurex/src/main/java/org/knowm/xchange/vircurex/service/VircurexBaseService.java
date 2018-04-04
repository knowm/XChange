package org.knowm.xchange.vircurex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.vircurex.VircurexAuthenticated;
import si.mazi.rescu.RestProxyFactory;

public class VircurexBaseService extends BaseExchangeService implements BaseService {

  protected VircurexAuthenticated vircurexAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexBaseService(Exchange exchange) {

    super(exchange);
    this.vircurexAuthenticated =
        RestProxyFactory.createProxy(
            VircurexAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }
}
