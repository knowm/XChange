package org.knowm.xchange.bitcoinaverage.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
