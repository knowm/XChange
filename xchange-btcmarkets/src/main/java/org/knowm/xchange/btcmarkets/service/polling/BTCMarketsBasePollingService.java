package org.knowm.xchange.btcmarkets.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class BTCMarketsBasePollingService extends BaseExchangeService implements BasePollingService {

  public BTCMarketsBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
