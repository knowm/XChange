package org.knowm.xchange.mexbt.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class MeXBTBasePollingService extends BaseExchangeService implements BasePollingService {

  protected MeXBTBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
