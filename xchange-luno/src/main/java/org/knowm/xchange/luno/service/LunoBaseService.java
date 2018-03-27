package org.knowm.xchange.luno.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.luno.LunoAPI;
import org.knowm.xchange.luno.LunoAPIImpl;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class LunoBaseService extends BaseExchangeService implements BaseService {

  protected final LunoAPI lunoAPI;

  public LunoBaseService(Exchange exchange) {

    super(exchange);
    lunoAPI =
        new LunoAPIImpl(
            exchange.getExchangeSpecification().getApiKey(),
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }
}
