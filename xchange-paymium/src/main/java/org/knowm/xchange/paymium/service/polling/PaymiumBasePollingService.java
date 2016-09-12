package org.knowm.xchange.paymium.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.paymium.Paymium;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

public class PaymiumBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final Paymium Paymium;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected PaymiumBasePollingService(Exchange exchange) {

    super(exchange);

    this.Paymium = RestProxyFactory.createProxy(Paymium.class, exchange.getExchangeSpecification().getSslUri());
  }
}
