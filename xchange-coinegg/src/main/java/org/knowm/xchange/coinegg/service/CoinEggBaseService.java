package org.knowm.xchange.coinegg.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinegg.CoinEgg;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CoinEggBaseService extends BaseExchangeService implements BaseService {

  protected CoinEgg coinEgg;

  public CoinEggBaseService(Exchange exchange) {
    super(exchange);
    this.coinEgg =
        ExchangeRestProxyBuilder.forInterface(CoinEgg.class, exchange.getExchangeSpecification())
            .build();
  }
}
