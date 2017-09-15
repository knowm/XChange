package org.knowm.xchange.luno.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.luno.LunoAPI;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class LunoBaseService extends BaseExchangeService implements BaseService {

  protected final LunoAPI luno;

  public LunoBaseService(Exchange exchange, LunoAPI luno) {
    super(exchange);
    this.luno = luno;
  }
}
