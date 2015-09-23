package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BTCMarketsBasePollingService extends BaseExchangeService implements BasePollingService {

  public BTCMarketsBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
