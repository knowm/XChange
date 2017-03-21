package org.knowm.xchange.paymium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.paymium.Paymium;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

public class PaymiumBaseService extends BaseExchangeService implements BaseService {

  protected final Paymium Paymium;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected PaymiumBaseService(Exchange exchange) {

    super(exchange);

    this.Paymium = RestProxyFactory.createProxy(Paymium.class, exchange.getExchangeSpecification().getSslUri());
  }
}
