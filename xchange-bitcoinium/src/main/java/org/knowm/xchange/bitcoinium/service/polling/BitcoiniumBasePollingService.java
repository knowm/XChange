package org.knowm.xchange.bitcoinium.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

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
