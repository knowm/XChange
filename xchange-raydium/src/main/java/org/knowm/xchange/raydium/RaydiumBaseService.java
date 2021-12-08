package org.knowm.xchange.raydium;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class RaydiumBaseService extends BaseExchangeService implements BaseService {

  protected final Raydium raydium;

  public RaydiumBaseService(Exchange exchange) {
    super(exchange);
    raydium =
        ExchangeRestProxyBuilder.forInterface(Raydium.class, exchange.getExchangeSpecification())
            .build();
  }
}
