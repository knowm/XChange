package com.xeiam.xchange.mexbt.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class MeXBTBasePollingService extends BaseExchangeService implements BasePollingService {

  protected MeXBTBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
