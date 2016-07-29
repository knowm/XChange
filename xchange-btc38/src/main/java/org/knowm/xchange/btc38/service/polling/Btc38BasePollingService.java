package org.knowm.xchange.btc38.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.btc38.Btc38;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 12/18/2014.
 */
public class Btc38BasePollingService<T extends Btc38> extends BaseExchangeService implements BasePollingService {

  protected final T btc38;

  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchange The {@link org.knowm.xchange.Exchange}
   */
  protected Btc38BasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);

    this.btc38 = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

}