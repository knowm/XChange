package org.knowm.xchange.yacuna.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;
import org.knowm.xchange.yacuna.Yacuna;

import si.mazi.rescu.RestProxyFactory;

/**
 * Created by Yingzhe on 12/27/2014.
 */
public class YacunaBasePollingService<T extends Yacuna> extends BaseExchangeService implements BasePollingService {

  protected final T yacuna;

  protected YacunaBasePollingService(Class<T> type, Exchange exchange) {

    super(exchange);
    this.yacuna = RestProxyFactory.createProxy(type, exchange.getExchangeSpecification().getSslUri());
  }

}
