package com.xeiam.xchange.therock.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class TheRockBasePollingService extends BaseExchangeService implements BasePollingService {

  public TheRockBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
