package com.xeiam.xchange.bitcoincharts.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

public class BitcoinChartsBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinChartsBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
