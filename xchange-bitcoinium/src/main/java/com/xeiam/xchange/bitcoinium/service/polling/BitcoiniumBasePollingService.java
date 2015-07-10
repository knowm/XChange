package com.xeiam.xchange.bitcoinium.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitcoiniumBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoiniumBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
