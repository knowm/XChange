package org.knowm.xchange.coinegg.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinegg.CoinEgg;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.RestProxyFactory;

public class CoinEggBaseService extends BaseExchangeService implements BaseService {

  protected CoinEgg coinEgg;

  public CoinEggBaseService(Exchange exchange) {
    super(exchange);
    this.coinEgg =
        RestProxyFactory.createProxy(
            CoinEgg.class, exchange.getExchangeSpecification().getSslUri());
  }
}
