package org.knowm.xchange.vircurex.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.vircurex.VircurexAuthenticated;

import si.mazi.rescu.RestProxyFactory;

public class VircurexBasePollingService extends BaseExchangeService implements BasePollingService {

  protected VircurexAuthenticated vircurexAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  public VircurexBasePollingService(Exchange exchange) {

    super(exchange);
    this.vircurexAuthenticated = RestProxyFactory.createProxy(VircurexAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
  }
}
