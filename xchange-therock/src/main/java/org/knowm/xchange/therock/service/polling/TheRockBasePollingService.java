package org.knowm.xchange.therock.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class TheRockBasePollingService extends BaseExchangeService implements BasePollingService {

  public TheRockBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
