package org.knowm.xchange.bitso.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class BitsoBasePollingService extends BaseExchangeService implements BasePollingService {

  public BitsoBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
