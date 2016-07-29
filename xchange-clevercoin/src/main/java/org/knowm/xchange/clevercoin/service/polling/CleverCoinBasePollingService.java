package org.knowm.xchange.clevercoin.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

/**
 * @author timmolter
 */
public class CleverCoinBasePollingService extends BaseExchangeService implements BasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CleverCoinBasePollingService(Exchange exchange) {

    super(exchange);
  }
}
