package com.xeiam.xchange.loyalbit.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class LoyalbitBasePollingService extends BaseExchangeService implements BasePollingService {

  public LoyalbitBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
