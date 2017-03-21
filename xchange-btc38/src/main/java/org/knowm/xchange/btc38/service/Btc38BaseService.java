package org.knowm.xchange.btc38.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btc38.Btc38;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38BaseService<T extends Btc38> extends BaseExchangeService implements BaseService {

  protected final T btc38;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchange The {@link org.knowm.xchange.Exchange}
   */
  protected Btc38BaseService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.btc38 = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

}