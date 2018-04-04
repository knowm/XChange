package org.knowm.xchange.zaif.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.zaif.Zaif;
import si.mazi.rescu.RestProxyFactory;

public class ZaifBaseService extends BaseExchangeService implements BaseService {

  protected final Zaif zaif;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected ZaifBaseService(Exchange exchange) {
    super(exchange);
    this.zaif =
        RestProxyFactory.createProxy(
            Zaif.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }
}
