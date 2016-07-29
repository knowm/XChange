package org.knowm.xchange.loyalbit.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

public class LoyalbitBasePollingService extends BaseExchangeService implements BasePollingService {

  public LoyalbitBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
