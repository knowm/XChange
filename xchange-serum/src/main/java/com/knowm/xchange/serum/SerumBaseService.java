package com.knowm.xchange.serum;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class SerumBaseService extends BaseExchangeService implements BaseService {

  protected final Serum serum;

  public SerumBaseService(Exchange exchange) {
    super(exchange);
    serum =
        ExchangeRestProxyBuilder.forInterface(Serum.class, exchange.getExchangeSpecification())
            .build();
  }
}
