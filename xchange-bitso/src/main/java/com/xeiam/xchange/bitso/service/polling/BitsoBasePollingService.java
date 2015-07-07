package com.xeiam.xchange.bitso.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitsoBasePollingService extends BaseExchangeService implements BasePollingService {

  public BitsoBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
