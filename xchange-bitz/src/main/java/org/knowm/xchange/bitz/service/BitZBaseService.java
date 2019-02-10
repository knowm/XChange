package org.knowm.xchange.bitz.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitz.BitZ;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class BitZBaseService extends BaseExchangeService implements BaseService {

  protected BitZ bitz;

  public BitZBaseService(Exchange exchange) {
    super(exchange);
    this.bitz =
        RestProxyFactory.createProxy(BitZ.class, exchange.getExchangeSpecification().getSslUri());
  }
}
