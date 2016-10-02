package org.knowm.xchange.quadrigacx.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class QuadrigaCxBasePollingService extends BaseExchangeService implements BasePollingService {

  public QuadrigaCxBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
