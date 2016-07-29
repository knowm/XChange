package org.knowm.xchange.bitcoincharts.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
