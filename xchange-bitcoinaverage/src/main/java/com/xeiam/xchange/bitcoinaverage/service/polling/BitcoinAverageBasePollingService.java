package com.xeiam.xchange.bitcoinaverage.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitcoinAverageBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinAverageBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
