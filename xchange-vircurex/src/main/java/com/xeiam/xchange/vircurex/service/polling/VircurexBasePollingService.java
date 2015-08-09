package com.xeiam.xchange.vircurex.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;
import com.xeiam.xchange.vircurex.VircurexAuthenticated;

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
