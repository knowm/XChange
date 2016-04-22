package org.knowm.xchange.independentreserve.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * Author: Kamil Zbikowski Date: 4/9/15
 */
public class IndependentReserveBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected IndependentReserveBasePollingService(Exchange exchange) {
    super(exchange);
  }
}
